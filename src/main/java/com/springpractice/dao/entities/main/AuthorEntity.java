package com.springpractice.dao.entities.main;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Table(name = "author")
@Entity
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(50)")
    private String name;

    @OneToMany(mappedBy = "authorEntity", cascade = CascadeType.PERSIST)
    private List<BookEntity> books = new ArrayList<>();

    protected AuthorEntity() {
    }

    public AuthorEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<BookEntity> getBooks() {
        return books;
    }


}
