package com.cards.cards.dao;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String password;
    private Date date;
    private String email;
    private String phone;
}
