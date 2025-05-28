package com.cards.cards.services;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    public UserDao createUser(UserDao user) {

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
        _userRepository.save(newUser);
        _emailRepository.save(new EmailData(newUser, user.getEmail()));
        return user;
    }

    @Transactional
    public String deleteUser(DeleteUserDao user) {
        UserModel deleteUserId = _userRepository.findFirstByName(user.getName());
        _emailRepository.deleteAll(_emailRepository.findByUserId(deleteUserId));
        _userRepository.delete(deleteUserId);
        return "User has deleted.";
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
