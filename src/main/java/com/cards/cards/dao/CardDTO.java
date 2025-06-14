package com.cards.cards.dao;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import com.cards.cards.models.AccountModel.Status;

@Getter
@Setter
public class CardDTO {

    private String number;
    private Date expire;
    private Status status;
    private BigDecimal balance;
}
