package com.cards.cards.dao;

import java.util.Optional;

import lombok.Data;

@Data
public class LoginUserDAO {
    private Optional<String> email = Optional.empty();
    private Optional<String> phone = Optional.empty();
    private Optional<String> password = Optional.empty();
}
