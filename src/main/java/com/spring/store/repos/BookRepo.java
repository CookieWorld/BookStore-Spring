package com.spring.store.repos;

import com.spring.store.entity.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepo extends CrudRepository<Book, Long> {
    @Query(value = "SELECT * FROM books WHERE LOWER(author) LIKE LOWER(CONCAT('%', :filter,'%')) OR LOWER(name) LIKE LOWER(CONCAT('%', :filter,'%'))", nativeQuery = true)
    List<Book> findBookByAuthorBOrNameContains(String filter);

    List<Book> findAll();

    Book getById(Long id);
}
