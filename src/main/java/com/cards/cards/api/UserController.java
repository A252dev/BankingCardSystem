package com.cards.cards.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cards.cards.models.UserModel;
import com.cards.cards.services.JwtService;
import com.cards.cards.services.UserService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/test")
    public String test() {
        return "test.";
    }

    @PostMapping("/user-create")
    public UserModel register(@RequestBody UserModel user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserModel user) {
        return new ResponseEntity<String>(jwtService.generateToken(user.getUsername()), HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public String getUser(@RequestHeader (name = "Authorization") String token) {

        String user = SecurityContextHolder.getContext().getAuthentication().getName();

        return new String("hi.");
    }
}
