package com.cards.cards.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cards.cards.models.UserModel;
import java.util.List;
import java.sql.Date;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    UserModel findFirstByName(String name);

    Optional<UserModel> findById(Integer user_id);

    @Query("SELECT u FROM UserModel u WHERE u.date >= :birthday")
    List<UserModel> findByDate(@Param("birthday") Date birthday);
}
