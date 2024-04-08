package com.example.userservice.dao;

import com.example.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Optional<User> findById(Long id);

    List<User> findAll();

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.type = :type WHERE u.email = :email")
    void updateUserType(String email, String type);
}


