package com.cards.cards.queues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cards.cards.services.QueueService;

@Component
public class Queues {

    @Autowired
    private QueueService _queueService;

    @Scheduled(fixedRate = 5000, initialDelay = 2000) // 5/2 seconds
    private void addBalance() {
        _queueService.addUserBalance();
    }
}
