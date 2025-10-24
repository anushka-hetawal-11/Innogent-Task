package com.library.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int stock;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;


}
