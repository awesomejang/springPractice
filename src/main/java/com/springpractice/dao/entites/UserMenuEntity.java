package com.springpractice.dao.entites;

import jakarta.persistence.*;

@Entity
@Table(name = "user_menu")
public class UserMenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu_name", nullable = false, columnDefinition = "VARCHAR(500)")
    private String menuName;

    @Column(name = "menu_url", nullable = false, columnDefinition = "VARCHAR(500)")
    private String menuUrl;

    @Column(name = "description", nullable = false, columnDefinition = "VARCHAR(500)")
    private String description;
}
