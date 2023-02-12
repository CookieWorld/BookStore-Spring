package com.spring.store.service;

import com.spring.store.entity.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {
    List<Book> findAll();

    List<Book> findAllWithFilter(String filter);
    Book addBook(Book book, MultipartFile file) throws IOException;

    Book editBook(Book book);

    void deleteBook(Long id);
}
