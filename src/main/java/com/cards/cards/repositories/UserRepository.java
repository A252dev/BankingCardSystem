package com.cards.cards.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cards.cards.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    UserModel findFirstByName(String name);

    Optional<UserModel> findById(Integer user_id);
}
