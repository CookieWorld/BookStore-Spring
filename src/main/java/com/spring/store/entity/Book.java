package com.spring.store.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 2, message = "Название книги должно быть не меньше 2-ух символов")
    private String name;
    @NotBlank(message = "Поле не может быть пустым")
    private String firstName;
    @NotBlank(message = "Поле не может быть пустым")
    private String lastName;

    private String author;

    @Min(value = 1, message = "Цена должна быть больше нуля")
    private Integer price;

    @Min(value = 0, message = "Количество не может быть отрицательным")
    private Integer quantity;

    private String filename;

    private boolean isRemoved;
}
