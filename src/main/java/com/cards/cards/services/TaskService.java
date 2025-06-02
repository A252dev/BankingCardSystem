package com.cards.cards.services;

import org.springframework.stereotype.Service;

import com.cards.cards.repositories.AccountRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaskService {

    private final AccountRepository _accountRepository;

    public void addUserBalance() {
        System.out.println("hi");
    }
}
