package com.mirkin_k_l.coursework.repository;

import com.mirkin_k_l.coursework.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    void deleteByEmail(String email);

    boolean existsByEmail(String email);
}
