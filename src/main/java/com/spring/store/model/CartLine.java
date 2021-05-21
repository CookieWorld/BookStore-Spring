package com.spring.store.model;

import org.springframework.stereotype.Component;

@Component
public class CartLine {
    private Book book;
    Integer quantity;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
