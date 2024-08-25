package com.springpractice.dao.entites;

import com.springpractice.dao.enums.AuthStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_auth")
@Getter
public class UserAuthEntity extends BaseTimeEntity{

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

//    @Column(name = "created_date", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
//    private LocalDateTime createdDate;
//
//    @Column(name = "updated_dated", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
//    private LocalDateTime updatedDate;

    protected UserAuthEntity() {
    }

    public UserAuthEntity(String clientId, String clientSecret, AuthStatusEnum authStatus, LocalDateTime expirationDate) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authStatus = authStatus;
        this.expirationDate = expirationDate;
    }

}
