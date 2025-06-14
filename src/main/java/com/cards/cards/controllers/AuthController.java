package com.cards.cards.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cards.cards.dao.LoginUserDAO;
import com.cards.cards.dao.UserDTO;
import com.cards.cards.services.JwtService;
import com.cards.cards.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "User creation and authentication")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody LoginUserDAO user) {
        String jwtToken = jwtService.generateToken(user);
        if (jwtToken != null)
            return new ResponseEntity<String>(jwtToken, HttpStatus.OK);
        return new ResponseEntity<String>("Data is incorrect!", HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/user-create")
    public ResponseEntity<Throwable> register(@RequestBody UserDTO user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userService.createUser(user);
    }
}
