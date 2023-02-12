package com.spring.store.controller;

import com.spring.store.entity.Order;
import com.spring.store.entity.User;
import com.spring.store.model.Cart;
import com.spring.store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/checkout")
    public Order createOrder(@RequestBody Cart cart, Principal user) {
        return cartService.createOrder(cart, user);
    }
}
