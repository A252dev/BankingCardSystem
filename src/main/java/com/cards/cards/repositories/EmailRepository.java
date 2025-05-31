package com.cards.cards.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cards.cards.models.EmailModel;
import com.cards.cards.models.UserModel;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<EmailModel, Integer> {

    EmailModel findByEmail(String email);

    @Query("SELECT e FROM EmailModel e WHERE e.user_id = ?1")
    List<EmailModel> findByUserId(@Param("user_id") UserModel user_id);

    @Modifying
    @Query("UPDATE EmailModel e SET e.email = ?2 WHERE e.user_id = ?1")
    void updateUser(@Param("user_id") UserModel user_id, @Param("email") String email);
}
