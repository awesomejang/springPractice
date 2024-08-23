package com.springpractice.dao.entites;

import com.springpractice.dao.enums.AuthStatusEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_auth")
public class UserAuthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false, columnDefinition = "VARCHAR(500)")
    private String clientId;

    @Column(name = "client_secret", nullable = false, columnDefinition = "VARCHAR(500)")
    private String clientSecret;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_status", nullable = false, columnDefinition = "ENUM('ACTIVE', 'INACTIVE')")
    private AuthStatusEnum authStatus;

    @Column(name = "expiration_date", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime expirationDate;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;
}
