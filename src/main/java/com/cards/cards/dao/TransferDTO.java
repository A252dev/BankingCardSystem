package com.cards.cards.dao;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class TransferDTO {
    private String number_from;
    private String number_to;
    private BigDecimal amount;
}
