package com.spring.store.controller;

import com.spring.store.entity.User;
import com.spring.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public User getUser(@RequestParam String username) {
        return userService.findByUsername(username);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getUsers")
    public List<User> getUsers() {
        return userService.getAll();
    }

    @PatchMapping("/update")
    public User updateUser(@RequestBody User user) {
        return userService.update(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
