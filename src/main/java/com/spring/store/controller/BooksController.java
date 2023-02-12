package com.spring.store.controller;

import com.spring.store.entity.Book;
import com.spring.store.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/books")
public class BooksController {

    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(value = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public Book addBook(@RequestPart("book") Book book, @RequestPart("file") MultipartFile file) throws IOException {
        return bookService.addBook(book, file);
    }

    @PatchMapping("/edit")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public Book editBook(@RequestBody Book book) {
        return bookService.editBook(book);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
