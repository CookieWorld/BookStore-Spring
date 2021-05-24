package com.spring.store.controller;

import com.spring.store.model.Book;
import com.spring.store.model.Cart;
import com.spring.store.model.CartLine;
import com.spring.store.model.User;
import com.spring.store.repos.BookRepo;
import com.spring.store.service.CartService;
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
import java.util.Optional;
import java.util.Set;


@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private Cart cart;

    @Autowired
    private BookRepo bookRepo;

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
                    if (cartService.checkout(quantity, book, c)) return "redirect:/cart";
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
                    if (cartService.checkout(quantity - c.getQuantity(), book, c)) return "redirect:/cart";
                }
            }
        }
        return "redirect:/cart";
    }

    @Transactional
    @PostMapping("/order")
    public String order(@RequestParam("userId") User user) throws MessagingException, UnsupportedEncodingException {
        cartService.order(user);
        return "redirect:/cart";
    }

}
