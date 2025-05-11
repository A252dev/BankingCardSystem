package com.cards.cards.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cards.cards.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
