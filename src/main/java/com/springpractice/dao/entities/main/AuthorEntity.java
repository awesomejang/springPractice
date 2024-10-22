package com.springpractice.dao.entities.main;

import jakarta.persistence.*;

@Table(name = "author")
@Entity
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "VARCHAR(50)")
    private String name;

    

    protected AuthorEntity() {
    }

    public AuthorEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }


}
