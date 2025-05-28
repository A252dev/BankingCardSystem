package com.cards.cards.services;

import java.sql.Date;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    public UserDao saveUser(UserDao user) {

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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = _userRepository.findByName(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        } else {
            return user;
        }
    }
}
