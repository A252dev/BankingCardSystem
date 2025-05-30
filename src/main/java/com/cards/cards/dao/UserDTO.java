package com.cards.cards.dao;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Optional;

import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String password;
    private Date date;
    private Optional<BigDecimal> balance = Optional.empty();
    private String email;
    private String phone;
}
