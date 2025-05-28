package com.cards.cards.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cards.cards.models.EmailData;
import com.cards.cards.models.UserModel;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<EmailData, Integer> {
    EmailData findByEmail(String email);

    @Query("SELECT e FROM EmailData e WHERE e.user_id = :user_id")
    List<EmailData> findByUserId(@Param("user_id") UserModel user_id);
}
