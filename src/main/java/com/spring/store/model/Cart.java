package com.spring.store.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Data
@NoArgsConstructor
public class Cart {
    Set<CartLine> cartLineList = new HashSet<>();

    Integer totalPrice = 0;
}
