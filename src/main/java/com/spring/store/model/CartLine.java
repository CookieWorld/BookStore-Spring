package com.spring.store.model;

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
