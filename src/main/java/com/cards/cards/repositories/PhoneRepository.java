package com.cards.cards.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cards.cards.models.PhoneData;

public interface PhoneRepository extends JpaRepository<PhoneData, Integer> {
}
