package com.cards.cards.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cards.cards.models.AccountModel;
import com.cards.cards.repositories.AccountRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TaskService {

    private final AccountRepository _accountRepository;

    public void addUserBalance() {

        List<AccountModel> listUsers = _accountRepository.findAll();
        for (AccountModel user : listUsers) {
            // something...
        }

    }
}
