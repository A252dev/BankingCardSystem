package com.cards.cards.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cards.cards.dao.SearchDAO;
import com.cards.cards.dao.TransferDTO;
import com.cards.cards.dao.UserDTO;
import com.cards.cards.models.UserModel;
import com.cards.cards.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@AllArgsConstructor
@Tag(name = "User actions")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping(path = "/user-update")
    public ResponseEntity<Throwable> update(@RequestBody UserDTO user) {
        return userService.updateUser(user);
    }

    @DeleteMapping(path = "/user-delete")
    public ResponseEntity<Throwable> delete() {
        return userService.deleteUser();
    }

    @PostMapping(path = "/transfer")
    public ResponseEntity<Throwable> transfer(@RequestBody TransferDTO transferDTO) {
        return userService.transfer(transferDTO);
    }

    @GetMapping(path = "/users")
    @Tag(name = "Information")
    public ResponseEntity<List<UserModel>> getUsers() {
        return new ResponseEntity<List<UserModel>>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(path = "/user-search")
    public ResponseEntity<Object> searchUser(@RequestBody SearchDAO searchDAO) {
        return new ResponseEntity<Object>(userService.searchUsers(searchDAO), HttpStatus.OK);
    }
}
