package com.cards.cards.models;

import java.math.BigDecimal;
import java.text.DateFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "number")
    private long number;

    @Column(name = "owner")
    private String owner;

    @Column(name = "expire")
    private DateFormat expire;

    @Column(name = "status")
    private Status status;

    @Column(name = "balance")
    private BigDecimal balance;

    private enum Status {
        ACTIVE,
        BLOCKED,
        EXPIRED
    }
}
