package com.spring.store.service.impl;

import com.spring.store.entity.Book;
import com.spring.store.repos.BookRepo;
import com.spring.store.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepo bookRepo;

    @Autowired
    public BookServiceImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public Book addBook(Book book) {
        return bookRepo.save(book);
    }
}
