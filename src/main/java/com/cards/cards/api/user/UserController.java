package com.cards.cards.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/register")
    public UserModel register(@RequestBody UserModel user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userService.saveUser(user);
        // return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserModel user) {

        return new ResponseEntity<String>(jwtService.generateToken(user.getUsername()), HttpStatus.ACCEPTED);

        // return new ResponseEntity<String>(jwtService.verify(user), HttpStatus.ACCEPTED);

        // SecurityContextHolder.getContext().setAuthentication(authentication);
        // return new ResponseEntity<String>(user.getEmail() + " " + user.getPassword(),
        // HttpStatus.ACCEPTED);
    }

    @GetMapping("/user")
    public String getUser() {

        String user = SecurityContextHolder.getContext().getAuthentication().getName();

        return new String(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
