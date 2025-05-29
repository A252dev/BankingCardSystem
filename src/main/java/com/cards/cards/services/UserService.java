package com.cards.cards.services;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import com.cards.cards.dao.DeleteUserDao;
import com.cards.cards.dao.UserDao;
import com.cards.cards.models.EmailData;
import com.cards.cards.models.UserModel;
import com.cards.cards.repositories.EmailRepository;
import com.cards.cards.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository _userRepository;

    @Autowired
    private EmailRepository _emailRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<String> createUser(UserDao user) {

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
            _emailRepository.save(new EmailData(newUser, user.getEmail()));
            return new ResponseEntity<String>("User has created.", HttpStatus.OK);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<String> deleteUser(DeleteUserDao user) {
        UserModel deleteUserId = _userRepository.findFirstByName(user.getName());
        if (deleteUserId != null) {
            _emailRepository.deleteAll(_emailRepository.findByUserId(deleteUserId));
            _userRepository.delete(deleteUserId);
            return new ResponseEntity<String>("User has deleted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("User not found!", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<String> updateUser(UserDao user) {
        UserModel updateUser = _userRepository.findFirstByName(user.getName());
        if (updateUser != null) {
            _emailRepository.updateUser(updateUser, user.getEmail());
            _userRepository.save(new UserModel(updateUser.getId(), user.getName(), user.getDate(), user.getPassword()));
            return new ResponseEntity<String>("User has updated.", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("User not found!", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserModel user = _userRepository.findFirstByName(name);
        if (user == null) {
            throw new UsernameNotFoundException("User " + name + " not found");
        } else {
            return user;
        }
    }
}
