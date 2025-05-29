package com.cards.cards.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cards.cards.models.UserModel;
import com.cards.cards.services.JwtService;

@RestController
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserModel user) {
        return new ResponseEntity<String>(jwtService.generateToken(user.getUsername()), HttpStatus.OK);
    }
}
