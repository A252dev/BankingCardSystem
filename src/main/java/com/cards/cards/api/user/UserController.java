package com.cards.cards.api.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cards.cards.models.AuthenticationResponse;
import com.cards.cards.models.User;
import com.cards.cards.services.JwtService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@AllArgsConstructor
public class UserController {

    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    @GetMapping("/test")
    public String test() {
        return "hi.";
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(), user.getPassword()));

        return new ResponseEntity<String>(jwtService.generateToken(user.getEmail()), HttpStatus.ACCEPTED);
        // if (authentication.isAuthenticated()) {
        //     return new ResponseEntity<String>(jwtService.generateToken(user.getEmail()), HttpStatus.ACCEPTED);
        // } else {
        //     throw new UsernameNotFoundException("Invalid user request");
        // }
        // return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
