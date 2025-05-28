package com.cards.cards.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cards.cards.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    UserModel findFirstByName(String name);
}
