package com.cards.cards.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cards.cards.models.User;
import com.cards.cards.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository _userRepository;

    public User saveUser(User user) {
        return _userRepository.save(user);
    }
}
