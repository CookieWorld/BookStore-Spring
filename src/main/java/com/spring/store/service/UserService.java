package com.spring.store.service;

import com.spring.store.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    User register(User user);

    User findByUsername(String username);

    User update(User user);

    List<User> getAll();

    void deleteUser(Long id);
}
