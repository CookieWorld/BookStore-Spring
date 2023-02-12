package com.spring.store.controller;

import com.spring.store.entity.Order;
import com.spring.store.entity.OrderLine;
import com.spring.store.exceptions.UserNotFoundExecption;
import com.spring.store.model.Status;
import com.spring.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public List<Order> getOrders(Principal user) throws UserNotFoundExecption {
        return orderService.getOrders(user);
    }

    @GetMapping("/getOrders")
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping("/{id}")
    public List<OrderLine> getOrderLinesById(@PathVariable("id") String id) {
        return orderService.getOrderLinesById(id);
    }

    @PatchMapping("/changeStatus/{id}")
    public Order sendOrder(@PathVariable("id") String id, @RequestBody Status status) {
        return orderService.changeStatusOfOrder(id, status);
    }
}
