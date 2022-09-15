/*
package com.spring.store.controller;

import com.spring.store.entity.Order;
import com.spring.store.model.Role;
import com.spring.store.entity.User;
import com.spring.store.service.OrderService;
import com.spring.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(*/
/*@AuthenticationPrincipal*//*
 User user, Model model) {
        model.addAttribute("users", userService.findAllExceptMySelf(user.getId()));
        return "userList";
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Boolean active,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user) {
        userService.saveUser(user, username, active, form);
        return "redirect:/user";
    }

    @GetMapping("/profile")
    public String getProfile(Model model, */
/*@AuthenticationPrincipal*//*
 User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("phone", user.getPhone());
        List<Order> order = orderService.getOrders(user);
        model.addAttribute("orders", order);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            */
/*@AuthenticationPrincipal*//*
 User user,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String phone) throws MessagingException, UnsupportedEncodingException {
        userService.updateProfile(user, email, password, phone);
        return "redirect:/user/profile";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/user";
    }
}
*/
