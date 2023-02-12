package com.spring.store.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Data
@NoArgsConstructor
public class Cart {
    List<CartLine> cartLineList = new ArrayList<>();

    Integer totalPrice = 0;
}
