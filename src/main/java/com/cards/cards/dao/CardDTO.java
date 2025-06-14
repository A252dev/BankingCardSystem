package com.cards.cards.dao;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDTO {

    private String number;
    private Date expire;
    private Status status;

    public enum Status {
        ACTIVE,
        BLOCKED,
        EXPIRED
    }
}
