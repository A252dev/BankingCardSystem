package com.cards.cards.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cards.cards.dao.UserDTO;
import com.cards.cards.models.EmailModel;
import com.cards.cards.models.PhoneModel;
import com.cards.cards.models.UserModel;
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

    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<String> createUser(UserDTO user) {

        /*
         * {
         * "name": "John",
         * "date": "1900-01-01",
         * "password": "password",
         * "email": "email@mail.com",
         * "phone": "78005553535"
         * }
         */

        UserModel newUser = new UserModel(user.getName(), user.getDate(), user.getPassword());
        if (_userRepository.findFirstByName(newUser.getName()) != null) {
            return new ResponseEntity<String>("The user exists!", HttpStatus.BAD_REQUEST);
        } else {
            _userRepository.save(newUser);
            _emailRepository.save(new EmailModel(newUser, user.getEmail()));
            _phoneRepository.save(new PhoneModel(newUser, user.getPhone()));
            return new ResponseEntity<String>("User has created.", HttpStatus.OK);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<String> deleteUser() {
        Optional<UserModel> deleteUserId = _userRepository
                .findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
        if (deleteUserId.isPresent()) {
            _phoneRepository.deleteByUserId(deleteUserId.get());
            _emailRepository.deleteAll(_emailRepository.findByUserId(deleteUserId.get()));
            _userRepository.delete(deleteUserId.get());
            return new ResponseEntity<String>("User has deleted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("User not found!", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<String> updateUser(UserDTO user) {
        Optional<UserModel> updateUser = _userRepository
                .findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()));
        if (updateUser.isPresent()) {
            _emailRepository.updateUser(updateUser.get(), user.getEmail());
            _userRepository.save(new UserModel(updateUser.get().getId(), user.getName(), user.getDate(),
                    passwordEncoder.encode(user.getPassword())));
            return new ResponseEntity<String>("User has updated.", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("User not found!", HttpStatus.NOT_FOUND);
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

    public EmailModel getUserEmailFromDatabase(String email) {
        return _emailRepository.findByEmail(email);
    }

    public PhoneModel getUserEmailByPhone(String phone) {
        return _phoneRepository.findByPhone(phone);
    }

    // public Optional<UserModel> getUserDataFromDatabase(UserModel model) {
    // return _userRepository.findById(model.getId());
    // }
}
