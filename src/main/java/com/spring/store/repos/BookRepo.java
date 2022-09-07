package com.spring.store.repos;

import com.spring.store.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface BookRepo extends CrudRepository<Book, Long> {
    Page<Book> findByAuthorContainsOrNameContains(@Param("author") String author, @Param("name") String name, Pageable pageable);

    Page<Book> findAll(Pageable pageable);
}
