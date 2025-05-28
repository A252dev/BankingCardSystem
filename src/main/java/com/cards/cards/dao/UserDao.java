package com.cards.cards.dao;

import java.math.BigDecimal;
import java.sql.Date;
import lombok.Data;

@Data
public class UserDao {
    private String name;
    private String password;
    private Date date;
    private BigDecimal balance = new BigDecimal(0.00);
    private String email;
    private String phone;
}
