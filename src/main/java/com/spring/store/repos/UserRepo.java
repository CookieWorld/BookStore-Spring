package com.spring.store.repos;

import com.spring.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepo extends CrudRepository<User, Long> {
    User findByUsername(String username);

    List<User> findAll();

    User findByActivationCode(String code);

    User findByEmail(String email);

    User findByResetPasswordToken(String token);

    User findByConfirmOrderToken(String token);

    List<User> findByIdNot(Long id);
}
