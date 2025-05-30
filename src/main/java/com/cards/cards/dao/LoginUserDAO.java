package com.cards.cards.dao;

import java.util.Optional;

import lombok.Data;

@Data
public class LoginUserDAO {
    private Optional<String> email;
    private Optional<String> phone;
    private String password;
}
