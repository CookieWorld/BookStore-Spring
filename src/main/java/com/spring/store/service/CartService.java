package com.spring.store.service;

import com.spring.store.entity.Order;
import com.spring.store.entity.User;
import com.spring.store.model.Cart;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.security.Principal;

public interface CartService {
    Order createOrder(Cart cart, Principal user);
}
