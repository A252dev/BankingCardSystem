package com.cards.cards.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cards.cards.models.EmailData;

@Repository
public interface EmailRepository extends JpaRepository<EmailData, Integer> {
    EmailData findByEmail(String email);
}
