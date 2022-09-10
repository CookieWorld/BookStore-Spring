package com.spring.store.repos;

import com.spring.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);

    User findByEmail(String email);

    User findByResetPasswordToken(String token);

    User findByConfirmOrderToken(String token);

    List<User> findByIdNot(Long id);
}
