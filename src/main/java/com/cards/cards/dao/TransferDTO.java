package com.cards.cards.dao;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // for the tests
public class TransferDTO {
    private String number_from;
    private String number_to;
    private BigDecimal amount;
}
