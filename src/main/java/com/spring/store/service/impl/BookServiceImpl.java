package com.spring.store.service.impl;

import com.spring.store.entity.Book;
import com.spring.store.repos.BookRepo;
import com.spring.store.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {
    @Value("${upload.path}")
    private String uploadPath;
    private final BookRepo bookRepo;

    @Autowired
    public BookServiceImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public List<Book> findAll() {
        return bookRepo.findAll();
    }

    @Override
    public List<Book> findAllWithFilter(String filter) {
        return bookRepo.findBookByAuthorBOrNameContains(filter);
    }

    @Override
    public Book addBook(Book book, MultipartFile file) throws IOException {
        if (file != null) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            try {
                file.transferTo(new File(uploadPath + "/" + resultFilename));
            } catch (IOException | IllegalStateException e) {
                throw new RuntimeException(e);
            }

            book.setAuthor(book.getFirstName() + " " + book.getLastName());

            book.setFilename(resultFilename);
        }

        return bookRepo.save(book);
    }

    @Override
    public Book editBook(Book book) {
        return bookRepo.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepo.getById(id);
        if (book != null) {
            book.setRemoved(true);
        } else throw new NoSuchElementException("Book with id: " + id + " not found");
        bookRepo.save(book);
    }
}
