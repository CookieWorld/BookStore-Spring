package com.spring.store.repos;

import com.spring.store.model.Order;
import com.spring.store.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepo extends CrudRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}
