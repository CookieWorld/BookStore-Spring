package com.spring.store.service.impl;

import com.spring.store.entity.Order;
import com.spring.store.entity.OrderLine;
import com.spring.store.entity.User;
import com.spring.store.exceptions.UserNotFoundExecption;
import com.spring.store.model.Status;
import com.spring.store.repos.OrderLineRepo;
import com.spring.store.repos.OrderRepo;
import com.spring.store.repos.UserRepo;
import com.spring.store.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final OrderLineRepo orderLineRepo;
    private final UserRepo userRepo;

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo, OrderLineRepo orderLineRepo, UserRepo userRepo) {
        this.orderRepo = orderRepo;
        this.orderLineRepo = orderLineRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<Order> getOrders(Principal user) throws UserNotFoundExecption {
        User currentUser = userRepo.findByUsername(user.getName());

        if (currentUser != null) {
            return orderRepo.findOrdersByUser(currentUser);
        }
        throw new UserNotFoundExecption("User with username: " + currentUser.getUsername() + " not found");
    }

    @Override
    public List<Order> getOrders() {
        return orderRepo.findAll();
    }

    @Override
    public List<OrderLine> getOrderLinesById(String id) {
        return orderLineRepo.selectOrderLinesByOrderId(Long.valueOf(id));
    }

    @Override
    public Order changeStatusOfOrder(String id, Status status) {
        Order order = orderRepo.getOrderById(Long.valueOf(id));

        if (order != null) {
            order.setStatus(status);
        } else throw new NoSuchElementException("Order with id: " + id + " not found");

        return orderRepo.save(order);
    }
}
