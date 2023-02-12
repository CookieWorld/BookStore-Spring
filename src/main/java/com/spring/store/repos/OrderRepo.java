package com.spring.store.repos;

import com.spring.store.entity.Order;
import com.spring.store.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepo extends CrudRepository<Order, Long> {
    List<Order> findOrdersByUser(User user);
    List<Order> findAll();

    Order getOrderById(Long id);
}
