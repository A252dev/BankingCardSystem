package com.cards.cards.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cards.cards.dao.LoginUserDAO;
import com.cards.cards.services.JwtService;

@RestController
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginUserDAO user) {
        String jwtToken = jwtService.generateToken(user);
        if (jwtToken != null)
            return new ResponseEntity<String>(jwtToken, HttpStatus.OK);
        return new ResponseEntity<String>("Data is incorrect!", HttpStatus.NOT_FOUND);
    }
}
