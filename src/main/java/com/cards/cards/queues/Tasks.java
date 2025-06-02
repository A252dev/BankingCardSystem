package com.cards.cards.queues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cards.cards.services.TaskService;

@Component
public class Tasks {

    @Autowired
    private TaskService _taskService;

    @Scheduled(fixedRate = 5000, initialDelay = 2000) // 5/2 seconds
    private void addBalance() {
        _taskService.addUserBalance();
    }
}
