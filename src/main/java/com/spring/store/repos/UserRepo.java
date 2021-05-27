package com.spring.store.repos;

import com.spring.store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);

    User findByEmail(String email);

    User findByResetPasswordToken(String token);

    User findByConfirmOrderToken(String token);


    List<User> findByIdNot(Long id);
}
