package com.spring.store.service;

import com.spring.store.entity.Order;
import com.spring.store.entity.OrderLine;
import com.spring.store.exceptions.UserNotFoundExecption;
import com.spring.store.model.Status;
import org.aspectj.weaver.ast.Or;

import java.security.Principal;
import java.util.List;

public interface OrderService {
    List<Order> getOrders(Principal user) throws UserNotFoundExecption;

    List<Order> getOrders();

    List<OrderLine> getOrderLinesById(String id);

    Order changeStatusOfOrder(String id, Status status);
}
