package com.spring.store.service.impl;

import com.spring.store.entity.Book;
import com.spring.store.entity.Order;
import com.spring.store.entity.OrderLine;
import com.spring.store.entity.User;
import com.spring.store.model.*;
import com.spring.store.repos.BookRepo;
import com.spring.store.repos.OrderLineRepo;
import com.spring.store.repos.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CartService {
    private final Cart cart;
    private final BookRepo bookRepo;
    private final OrderRepo orderRepo;
    private final OrderLineRepo orderLineRepo;
    private final MailSender mailSender;

    @Autowired
    public CartService(Cart cart, BookRepo bookRepo, OrderRepo orderRepo, OrderLineRepo orderLineRepo, MailSender mailSender) {
        this.cart = cart;
        this.bookRepo = bookRepo;
        this.orderRepo = orderRepo;
        this.orderLineRepo = orderLineRepo;
        this.mailSender = mailSender;
    }

    public Order order(User user) throws MessagingException, UnsupportedEncodingException {
        Set<CartLine> cartLines = cart.getCartLineList();
        Order order = new Order();
        Set<OrderLine> orderLines = new HashSet<>();
        order.setUser(user);
        for (CartLine c : cartLines) {
            OrderLine orderLine = new OrderLine();
            orderLine.setQuantity(c.getQuantity());
            orderLine.setBook(c.getBook());
            orderLine.setPrice(c.getBook().getPrice());
            orderLine.setOrder(order);
            changeQuantity(c.getBook().getId(), c.getQuantity());
            orderLines.add(orderLine);
            order.setOrderLines(orderLines);
        }
        order.setTotalPrice(cart.getTotalPrice());
        orderRepo.save(order);
        for (OrderLine o : orderLines) {
            orderLineRepo.save(o);
        }
        cart.setTotalPrice(0);
        cart.setCartLineList(new HashSet<>());
        return order;
    }

    public boolean checkQuantity(Integer quantity, Book book, CartLine cartLine) {
        return cartLine.getQuantity() + quantity <= book.getQuantity();
    }

    public void processQuantity(Integer quantity, Book book, CartLine cartLine) {
        int quantityBook = cartLine.getQuantity() + quantity;
        cart.setTotalPrice(cart.getTotalPrice() - (cartLine.getQuantity() * book.getPrice()) + (quantityBook * book.getPrice()));
        cartLine.setQuantity(quantityBook);
    }

    public void sendMessage(User user, Order order, String token) throws MessagingException, UnsupportedEncodingException {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "<h1>Здравствуйте, %s! </h1>\n" +
                            "<p><h3>Заказ №%s от %s создан!</h3></p>" +
                            "<p>Вам необходимо подтвердить заказ. Перейдите по ссылке для подтверждения заказа:" +
                            "<a href = \"http://localhost:8080/confirm?token=%s&orderId=%s\">Подтвердить заказ</a></p>",
                    user.getUsername(),
                    order.getId(),
                    order.getDate(),
                    token,
                    order.getId()
            );
            mailSender.send(user.getEmail(), "Заказ создан", message);
        }
    }

    public void changeQuantity(Long id, int quantity) {
        Optional<Book> bookById = bookRepo.findById(id);
        if (bookById.isPresent()) {
            Book book = bookById.get();
            book.setQuantity(book.getQuantity() - quantity);
        }
    }
}
