package com.springpractice.dao.entities.main;

import com.springpractice.dao.entities.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "user_menu_mapping")
public class UserMenuMappingEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_auth_id", referencedColumnName = "id", nullable = false)
    private UserAuthEntity userAuthEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_menu_id", referencedColumnName = "id", nullable = false)
    private UserMenuEntity userMenuEntity;

    protected UserMenuMappingEntity() {
    }

    public UserMenuMappingEntity(UserAuthEntity userAuthEntity, UserMenuEntity userMenuEntity) {
        this.userAuthEntity = userAuthEntity;
        this.userMenuEntity = userMenuEntity;
    }

    public Long getId() {
        return id;
    }

    public UserAuthEntity getUserAuthEntity() {
        return userAuthEntity;
    }

    public UserMenuEntity getUserMenuEntity() {
        return userMenuEntity;
    }
}
