package com.spring.store.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "orderLine")
@Data
@NoArgsConstructor
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @JoinColumn(name = "book")
    @ManyToOne
    private Book book;

    private String author = null;

    private String name = null;

    private Integer price = null;

    @NotNull
    private int quantity;
}
