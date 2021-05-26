package com.spring.store.controller;

import com.spring.store.model.Order;
import com.spring.store.model.Role;
import com.spring.store.model.User;
import com.spring.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user")
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user/{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/user")
    public String userSave(
            @RequestParam String username,
            @RequestParam Boolean active,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user) {
        userService.saveUser(user, username, active,form);
        return "redirect:/user";
    }

    @GetMapping("/user/profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("phone", user.getPhone());
        List<Order> order = userService.getOrders(user);
        model.addAttribute("orders", order);
        return "profile";
    }

    @PostMapping("/user/profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String phone) throws MessagingException, UnsupportedEncodingException {
        userService.updateProfile(user, email, password, phone);
        return "redirect:/user/profile";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/orderList")
    public String listOfOrders(Model model) {
        Iterable<Order> ordersList = userService.getOrdersList();
        model.addAttribute("ordersList", ordersList);
        return "orderList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/orderList/{order}")
    public String listOfOrders(@PathVariable Order order, Model model) {
        model.addAttribute("orders", order);
        return "order";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/orders/{user}")
    public String listOfOrders(@PathVariable User user,Model model) {
        List<Order> order = userService.getOrders(user);
        model.addAttribute("orders", order);
        model.addAttribute("user", user);
        return "ordersUser";
    }

}
