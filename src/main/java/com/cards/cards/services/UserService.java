package com.cards.cards.services;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

        UserModel userModel = new UserModel(user.getName(), user.getDate(), user.getPassword());
        // UserModel userModel2 = new UserModel(0, "Name", new Date(1900 - 01 - 01), "pass");
        // System.out.println(userModel);
        _userRepository.save(userModel);
        EmailData emailData = new EmailData(userModel, user.getEmail());
        // emailData.setUser_id(userModel);
        // emailData.setEmail(user.getEmail());
        _emailRepository.save(emailData);
        return user;
        // emailData.setUser_id(userModel);
        // emailData.setEmail(user.getEmail());

        // userModel.setEmail_user(emailData);


        // return userModel;
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
