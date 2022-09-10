package com.spring.store.model;

import com.spring.store.entity.Book;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class CartLine {
    private Book book;
    Integer quantity;
}
