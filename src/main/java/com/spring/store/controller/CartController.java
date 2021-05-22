package com.spring.store.controller;

import com.spring.store.model.*;
import com.spring.store.repos.BookRepo;
import com.spring.store.repos.OrderLineRepo;
import com.spring.store.repos.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private Cart cart;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderLineRepo orderLineRepo;

    @GetMapping
    public String cart(Model model) {
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/addToCart")
    public String addToCart(Long id, Integer quantity) {
        Optional<Book> byId = bookRepo.findById(id);
        if (byId.isPresent()) {
            Book book = byId.get();
            CartLine cartLine = new CartLine();
            Set<CartLine> cartLines = cart.getCartLineList();

            cartLine.setBook(book);
            cartLine.setQuantity(quantity);

            for (CartLine c : cartLines) {
                if (c.getBook().getId().equals(id)) {
                    if (checkout(quantity, book, c)) return "redirect:/cart";
                    else {
                        return "redirect:/cart";
                    }
                }
            }
            cartLines.add(cartLine);
            cart.setTotalPrice(cart.getTotalPrice() + cartLine.getQuantity() * book.getPrice());
            cart.setCartLineList(cartLines);

        }
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam Long id) {
        Set<CartLine> cartLineList = cart.getCartLineList();
        for (CartLine c : cartLineList) {
            boolean isBook = c.getBook().getId().equals(id);
            if (isBook) {
                cart.setTotalPrice(cart.getTotalPrice() - c.getBook().getPrice() * c.getQuantity());
                cartLineList.remove(c);
                return "redirect:/cart";
            }
        }
        return "redirect:/cart";
    }

    @PostMapping("/editQuantity")
    public String quantityUpdate(
            @RequestParam Integer quantity,
            @RequestParam Long id) {
        Optional<Book> byId = bookRepo.findById(id);
        if (byId.isPresent()) {
            Book book = byId.get();
            Set<CartLine> cartLines = cart.getCartLineList();
            for (CartLine c : cartLines) {
                if (c.getBook().getId().equals(id)) {
                    if (checkout(quantity - c.getQuantity(), book, c)) return "redirect:/cart";
                }
            }
        }
        return "redirect:/cart";
    }

    private boolean checkout(Integer quantity, Book book, CartLine cartLine) {
        if (cartLine.getQuantity() + quantity <= book.getQuantity()) {
            int quantityBook = cartLine.getQuantity() + quantity;
            cart.setTotalPrice(cart.getTotalPrice() - (cartLine.getQuantity() * book.getPrice()) + (quantityBook * book.getPrice()));
            cartLine.setQuantity(quantityBook);
            return true;
        } else return false;
    }

    @Transactional
    @PostMapping("/order")
    public String order(User user, Model model) {
        Set<CartLine> cartLines = cart.getCartLineList();
        Order order = new Order();
        Set<OrderLine> orderLines = new HashSet<>();
        order.setUser(user);
        for (CartLine c : cartLines) {
            OrderLine orderLine = new OrderLine();
            orderLine.setQuantity(c.getQuantity());
            orderLine.setBook(c.getBook());
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
        return "redirect:/cart";
    }

    private void changeQuantity(Long id, int quantity) {
        Optional<Book> bookById = bookRepo.findById(id);
        if (bookById.isPresent()) {
            Book book = bookById.get();
            book.setQuantity(book.getQuantity() - quantity);
        }
    }
}
