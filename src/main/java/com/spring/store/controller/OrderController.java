package com.spring.store.controller;

import com.spring.store.model.Order;
import com.spring.store.model.User;
import com.spring.store.service.OrderService;
import com.spring.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/order/cancel/{orderId}")
    public String cancel(@PathVariable("orderId") Long orderId, @RequestParam("profile") boolean isProfile, @RequestParam(value = "userId", required = false) Long userId) throws MessagingException, UnsupportedEncodingException {
        orderService.cancel(orderId);
        if (isProfile) {
            return "redirect:/user/profile";
        }
        return "redirect:/orders/" + userId;
    }

    @PostMapping("/order/send")
    public String send(@RequestParam("id") Long orderId, @RequestParam String trackCode) throws MessagingException, UnsupportedEncodingException {
        orderService.send(orderId, trackCode);
        return "redirect:/orderList";
    }

    @GetMapping("/order/delivered/{id}")
    public String delivered(@PathVariable("id") Long orderId, Model model) {
        orderService.delivered(orderId);
        User user = orderService.findUser(orderId);
        model.addAttribute("user", user);
        return "redirect:/user/profile";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping("/orderList")
    public String listOfOrders(Model model) {
        Iterable<Order> ordersList = orderService.getOrdersList();
        model.addAttribute("ordersList", ordersList);
        return "orderList";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping("/orderList/{order}")
    public String listOfOrders(@PathVariable Order order, Model model) {
        model.addAttribute("orders", order);
        return "order";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping("/orders/{user}")
    public String listOfOrders(@PathVariable User user, Model model) {
        List<Order> order = orderService.getOrders(user);
        model.addAttribute("orders", order);
        model.addAttribute("user", user);
        return "ordersUser";
    }

    @GetMapping("/confirm")
    public String showConfirmOrderPage(@Param(value = "token") String token, @Param(value = "orderId") Long orderId, Model model) {
        User user = userService.getByConfirmOrderToken(token);
        Order order = orderService.findOne(orderId);
        if (user == null) {
            model.addAttribute("messageType", "warning");
            model.addAttribute("message", "Неправильный токен");
            return "confirmOrder";
        }
        orderService.updateStatusOrder(user, order);
        model.addAttribute("messageType", "success");
        model.addAttribute("message", "Ваш заказ подтвержден!");
        return "confirmOrder";
    }
}
