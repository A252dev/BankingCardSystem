package com.cards.cards.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
    @Query("UPDATE AccountModel a SET a.balance = ?2 WHERE a.user_id = ?1")
    void updateBalance(@Param("user_id") UserModel user_id, @Param("balance") BigDecimal balance);

    @Modifying
    @Query("DELETE FROM AccountModel a WHERE a.user_id = ?1")
    void deleteByUserId(@Param("user_id") UserModel user_id);

    @Query("SELECT a FROM AccountModel a WHERE a.user_id = :user_id")
    Optional<AccountModel> findFirstByUser_id(@Param("user_id") UserModel user_id);

    @Query("SELECT a FROM AccountModel a WHERE a.user_id = :user_id")
    Optional<List<AccountModel>> findAllByUser_id(@Param("user_id") UserModel user_id);
}
