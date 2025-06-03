package com.cards.cards.repositories;

import java.util.List;
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
    @Query("DELETE FROM PhoneModel p WHERE p.user_id = :user_id")
    void deleteByUserId(@Param("user_id") UserModel user_id);

    PhoneModel findByPhone(String phone);

    @Query("SELECT p FROM PhoneModel p WHERE p.phone = :phone")
    List<PhoneModel> findAllByPhone(@Param("phone") String phone);

    @Modifying
    @Query("UPDATE PhoneModel p SET p.phone = :phone WHERE p.user_id = :user_id")
    void updateUser(@Param("user_id") UserModel user_id, @Param("phone") String phone);
}
