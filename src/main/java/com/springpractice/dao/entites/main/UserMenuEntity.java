package com.springpractice.dao.entites.main;

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

    protected UserMenuEntity() {
    }

    public UserMenuEntity(String menuName, String menuUrl, String description) {
        this.menuName = menuName;
        this.menuUrl = menuUrl;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public String getDescription() {
        return description;
    }
}
