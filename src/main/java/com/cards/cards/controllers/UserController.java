package com.cards.cards.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cards.cards.dao.TransferDTO;
import com.cards.cards.dao.UserDTO;
import com.cards.cards.models.EmailModel;
import com.cards.cards.models.UserModel;
import com.cards.cards.services.UserService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/user-update")
    public ResponseEntity<String> update(@RequestBody UserDTO user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/user-delete")
    public ResponseEntity<String> delete() {
        return userService.deleteUser();
    }

    @GetMapping("/user")
    public String getUser() {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return new String("hi. your user_id is: " + user);
    }

    @PostMapping("/transfer")
    public String transfer(@RequestBody TransferDTO transferDTO){
        return userService.transfer(transferDTO);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> getUsers() {
        return new ResponseEntity<List<UserModel>>(userService.getAllUsers(), HttpStatus.OK);
    }
}
