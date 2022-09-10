package com.spring.store.repos;

import com.spring.store.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepo extends CrudRepository<Book, Long> {
    List<Book> findByAuthorContainsOrNameContains(@Param("author") String author, @Param("name") String name);

    List<Book> findAll();
}
