package com.cards.cards.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cards.cards.models.PhoneModel;
import com.cards.cards.models.UserModel;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneModel, Integer> {
    @Modifying
    @Query("DELETE FROM PhoneData p WHERE p.user_id = :user_id")
    void deleteByUserId(@Param("user_id") UserModel user_id);
}
