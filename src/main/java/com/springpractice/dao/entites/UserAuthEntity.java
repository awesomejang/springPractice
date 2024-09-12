package com.springpractice.dao.entites;

import com.springpractice.dao.converter.UserAuthGradeConverter;
import com.springpractice.dao.enums.AuthStatusEnum;
import com.springpractice.dao.enums.UserAuthGradeEnum;
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

    @Convert(converter = UserAuthGradeConverter.class)
    @Column(name = "user_grade", nullable = false, columnDefinition = "VARCHAR(20)")
    private UserAuthGradeEnum userAuthGrade;

    protected UserAuthEntity() {
    }

    public UserAuthEntity(String clientId, String clientSecret, AuthStatusEnum authStatus, LocalDateTime expirationDate) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authStatus = authStatus;
        this.expirationDate = expirationDate;
    }

}
