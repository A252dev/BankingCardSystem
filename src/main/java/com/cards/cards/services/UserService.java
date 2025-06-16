package com.cards.cards.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.cards.cards.dao.SearchDAO;
import com.cards.cards.dao.UserDTO;
import com.cards.cards.exceptions.UserExceptions;
import com.cards.cards.models.EmailModel;
import com.cards.cards.models.PhoneModel;
import com.cards.cards.models.UserModel;
import com.cards.cards.repositories.AccountRepository;
import com.cards.cards.repositories.EmailRepository;
import com.cards.cards.repositories.PhoneRepository;
import com.cards.cards.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepository _userRepository;
    private final EmailRepository _emailRepository;
    private final PhoneRepository _phoneRepository;
    private final AccountRepository _accountRepository;

    private Optional<UserModel> findUserId = Optional.empty();

    @Autowired
    private UserExceptions userExceptions;

    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Throwable> createUser(UserDTO user) {
        UserModel newUser = new UserModel(user.getName(), user.getDate(), user.getPassword());
        if (!_emailRepository.findByEmail(user.getEmail()).isEmpty()
                || !_phoneRepository.findByPhone(user.getPhone()).isEmpty()) {
            return userExceptions.EmailOrPhoneIsExists();
        } else {
            _userRepository.save(newUser);
            _emailRepository.save(new EmailModel(newUser, user.getEmail()));
            _phoneRepository.save(new PhoneModel(newUser, user.getPhone()));
            // _accountRepository.save(new AccountModel(newUser, BigDecimal.valueOf(0.0)));
            return userExceptions.Created();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Throwable> deleteUser() {
        findUserId = this.getAuthUserId();
        if (findUserId.isPresent()) {
            _accountRepository.deleteByUserId(findUserId.get());
            _phoneRepository.deleteByUserId(findUserId.get());
            _emailRepository.deleteAll(_emailRepository.findByUserId(findUserId.get()));
            _userRepository.delete(findUserId.get());
            return userExceptions.Deleted();
        } else {
            return userExceptions.NotFound();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Throwable> updateUser(UserDTO user) {
        findUserId = this.getAuthUserId();
        if (findUserId.isPresent()) {
            if (_phoneRepository.findByPhone(user.getPhone()) != null)
                return userExceptions.PhoneIsExists();
            else
                _phoneRepository.updateUser(findUserId.get(), user.getPhone());
            if (_emailRepository.findByEmail(user.getEmail()) != null)
                return userExceptions.EmailIsExists();
            else
                _emailRepository.updateUser(findUserId.get(), user.getEmail());
            _userRepository.save(new UserModel(findUserId.get().getId(), user.getName(), user.getDate(),
                    passwordEncoder.encode(user.getPassword())));
            return userExceptions.Ok();
        } else {
            return userExceptions.NotFound();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<UserModel> user = _userRepository.findById(Integer.parseInt(name));
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User " + name + " not found");
        } else {
            return user.get();
        }
    }

    public Optional<EmailModel> getUserEmailFromDatabase(String email) {
        return _emailRepository.findByEmail(email);
    }

    public Optional<PhoneModel> getUserEmailByPhone(String phone) {
        return _phoneRepository.findByPhone(phone);
    }

    public Optional<UserModel> getAuthUserId() {
        return _userRepository
                .findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    public List<UserModel> getAllUsers() {
        return _userRepository.findAll();
    }

    public Object searchUsers(SearchDAO searchDAO) {
        if (searchDAO.getBirthday().isPresent()) {
            return _userRepository.findByDate(searchDAO.getBirthday().get()).toArray();
        }
        if (searchDAO.getEmail().isPresent()) {
            return _emailRepository.findListByEmail(searchDAO.getEmail().get()).toArray();
        }
        if (searchDAO.getPhone().isPresent()) {
            return _phoneRepository.findAllByPhone(searchDAO.getPhone().get());
        }
        return null;
    }
}
