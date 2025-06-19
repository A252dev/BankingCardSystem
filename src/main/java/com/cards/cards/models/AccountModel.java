package com.cards.cards.models;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "account")
@AllArgsConstructor
@RequiredArgsConstructor
public class AccountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "number", nullable = false)
    private String number;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserModel user_id;

    @Column(name = "expire", nullable = false, unique = false)
    private Date expire;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    public AccountModel(String number, UserModel user_id, Date expire, Status status, BigDecimal balance) {
        this.number = number;
        this.user_id = user_id;
        this.expire = expire;
        this.status = status;
        this.balance = balance;
    }

    // for the tests
    public AccountModel(String number, BigDecimal balance, UserModel user_id) {
        this.number = number;
        this.balance = balance;
        this.user_id = user_id;
    }

    public enum Status {
        ACTIVE,
        BLOCKED,
        EXPIRED
    }
}
