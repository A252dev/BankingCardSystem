package com.cards.cards.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cards.cards.dao.DeleteUserDao;
import com.cards.cards.dao.UserDao;
import com.cards.cards.services.UserService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user-create")
    public ResponseEntity<String> register(@RequestBody UserDao user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userService.createUser(user);
    }

    @PutMapping("/user-update")
    public ResponseEntity<String> update(@RequestBody UserDao user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/user-delete")
    public ResponseEntity<String> delete(@RequestBody DeleteUserDao user) {
        return userService.deleteUser(user);
    }

    @GetMapping("/user")
    public String getUser(@RequestHeader(name = "Authorization") String token) {

        String user = SecurityContextHolder.getContext().getAuthentication().getName();

        return new String("hi. " + user);
    }
}
