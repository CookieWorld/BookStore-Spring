package com.spring.store.entity;

import com.spring.store.entity.Book;
import com.spring.store.entity.Order;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "orderLine")
@RequiredArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "book")
    @ManyToOne
    @NonNull
    private Book book;

    @NonNull
    private int quantity;
}
