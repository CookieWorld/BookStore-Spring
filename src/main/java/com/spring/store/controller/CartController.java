/*
package com.spring.store.controller;

import com.spring.store.entity.Book;
import com.spring.store.entity.Order;
import com.spring.store.entity.User;
import com.spring.store.exceptions.UserNotFoundExecption;
import com.spring.store.model.*;
import com.spring.store.repos.BookRepo;
import com.spring.store.repos.UserRepo;
import com.spring.store.service.CartService;
import com.spring.store.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final Cart cart;
    private final BookRepo bookRepo;
    private final UserRepo userRepo;

    @Autowired
    public CartController(CartService cartService, UserService userService, Cart cart, BookRepo bookRepo, UserRepo userRepo) {
        this.cartService = cartService;
        this.userService = userService;
        this.cart = cart;
        this.bookRepo = bookRepo;
        this.userRepo = userRepo;
    }

    @GetMapping
    public String cart(Model model) {
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping("/addToCart")
    public String addToCart(Long id, Integer quantity, Model model) {
        Optional<Book> byId = bookRepo.findById(id);
        if (byId.isPresent()) {
            Book book = byId.get();
            CartLine cartLine = new CartLine();
            Set<CartLine> cartLines = cart.getCartLineList();

            cartLine.setBook(book);
            cartLine.setQuantity(quantity);

            for (CartLine c : cartLines) {
                if (c.getBook().getId().equals(id)) {
                    if (cartService.checkQuantity(quantity, book, c)) {
                        cartService.processQuantity(quantity, book, c);
                        return "redirect:/cart";
                    } else {
                        model.addAttribute("message", "На складе не хватает книг \"" + book.getName() + "\". Доступное количество " + book.getQuantity());
                        return cart(model);
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
    public String quantityUpdate(@RequestParam Integer quantity, @RequestParam Long id, Model model) {
        Optional<Book> byId = bookRepo.findById(id);
        if (byId.isPresent()) {
            Book book = byId.get();
            Set<CartLine> cartLines = cart.getCartLineList();
            for (CartLine c : cartLines) {
                if (c.getBook().getId().equals(id)) {
                    if (cartService.checkQuantity(quantity - c.getQuantity(), book, c)) {
                        cartService.processQuantity(quantity - c.getQuantity(), book, c);
                        return "redirect:/cart";
                    } else {
                        model.addAttribute("message", "На складе не хватает книг \"" + book.getName() + "\". Доступное количество " + book.getQuantity());
                        return cart(model);
                    }
                }
            }
        }
        return "redirect:/cart";
    }

    @GetMapping("/clear")
    public String clear() {
        cart.setTotalPrice(0);
        cart.setCartLineList(new HashSet<>());
        return "redirect:/cart";
    }

    @Transactional
    @PostMapping("/order")
    public String order(@RequestParam("userId") User user, @RequestParam(required = false, defaultValue = "false") Boolean save, @RequestParam String country, @RequestParam String address, Model model) throws MessagingException, UnsupportedEncodingException, UserNotFoundExecption {
        Order order = cartService.order(user);

        String token = RandomString.make(30);
        userService.updateConfirmOrderToken(token, user.getEmail());
        cartService.sendMessage(user, order, token);

        if (save) {
            Optional<User> userById = userRepo.findById(user.getId());
            User user1 = userById.get();
            user1.setCountry(country);
            user1.setAddress(address);
            userRepo.save(user1);
        }
        model.addAttribute("message", "Подтвердите свой заказ на почте");
        return "cart";
    }
}
*/
