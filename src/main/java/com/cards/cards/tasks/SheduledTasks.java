package com.cards.cards.tasks;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cards.cards.models.AccountModel;
import com.cards.cards.repositories.AccountRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SheduledTasks {

    private final AccountRepository _accountRepository;

    @Scheduled(fixedRate = 5000, initialDelay = 2000) // 5/2 seconds
    public void addUserBalance() {

        List<AccountModel> listUsers = _accountRepository.findAll();
        for (AccountModel user : listUsers) {
            if (user.getBalance().compareTo(BigDecimal.valueOf(120)) <= 0) {
                user.setBalance(user.getBalance().add(BigDecimal.valueOf(10)).setScale(2, RoundingMode.HALF_UP));
                _accountRepository.save(user);
            }
        }
    }
}
