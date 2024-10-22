package com.springpractice.dao.entities.main;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "book")
@Entity
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, columnDefinition = "VARCHAR(50)")
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    private AuthorEntity authorEntity;
}
