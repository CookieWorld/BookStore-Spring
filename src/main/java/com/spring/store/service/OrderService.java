package com.spring.store.service;

import com.spring.store.model.Order;
import com.spring.store.model.Status;
import com.spring.store.model.User;
import com.spring.store.repos.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final MailSender mailSender;

    @Autowired
    public OrderService(OrderRepo orderRepo, MailSender mailSender) {
        this.orderRepo = orderRepo;
        this.mailSender = mailSender;
    }

    public List<Order> getOrders(User user) {
        List<Order> allByUser = orderRepo.findAllByUser(user);
        return allByUser;
    }

    public Iterable<Order> getOrdersList() {
        Iterable<Order> orders = orderRepo.findAll();
        return orders;
    }

    public Order findOne(Long orderId) {
        return orderRepo.findById(orderId).get();
    }

    public User findUser(Long orderId) {
        Order one = findOne(orderId);
        return one.getUser();
    }

    public void cancel(Long orderId) throws MessagingException, UnsupportedEncodingException {
        Order order = findOne(orderId);
        order.setStatus(Status.Отменен);
        orderRepo.save(order);
        sendMessage("отменен", order);
    }

    public void delivered(Long orderId) {
        Order order = findOne(orderId);
        order.setStatus(Status.Доставлен);
        orderRepo.save(order);
    }

    public void send(Long orderId, String trackCode) throws MessagingException, UnsupportedEncodingException {
        Order order = findOne(orderId);
        order.setStatus(Status.Отправлен);
        order.setTrackCode(trackCode);
        orderRepo.save(order);
        sendMessage("отправлен", order);
    }

    public void sendMessage(String info, Order order) throws MessagingException, UnsupportedEncodingException {
        if (!StringUtils.isEmpty(order.getUser().getEmail())) {
            String message = String.format(
                    "<h1>Здравствуйте, %s! </h1>\n" +
                            "<p><h3>Ваш заказ №%s от %s %s!</h3></p>",
                    order.getUser().getUsername(),
                    order.getId(),
                    order.getDate(),
                    info
            );
            mailSender.send(order.getUser().getEmail(), "Заказ " + info, message);
        }
    }

    public void updateStatusOrder(User user, Order order) {
        user.setConfirmOrderToken(null);
        order.setStatus(Status.Подтвержден);
    }
}

