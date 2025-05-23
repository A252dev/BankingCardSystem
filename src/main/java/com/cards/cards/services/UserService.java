package com.cards.cards.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.cards.cards.models.UserModel;
import com.cards.cards.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository _userRepository;

    public UserModel saveUser(UserModel user) {
        return _userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserModel user = _userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        } else {
            return user;
        }
    }
}
