package com.cards.cards.api.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cards.cards.models.User;
import com.cards.cards.services.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@AllArgsConstructor
public class UserController {

    private JwtService jwtService;

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        return jwtService.generateToken();
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        return new ResponseEntity<String>("hi.", HttpStatus.ACCEPTED);
    }
}
