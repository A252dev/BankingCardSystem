package com.cards.cards.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cards.cards.models.AccountModel;
import com.cards.cards.models.UserModel;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Integer> {
    @Modifying
    @Query("DELETE FROM AccountModel p WHERE p.user_id = ?1")
    void deleteBalance(@Param("user_id") UserModel user_id);
}
