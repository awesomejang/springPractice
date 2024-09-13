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
    @Column(name = "user_grade", nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'NORMAL'")
    private UserAuthGradeEnum userAuthGrade = UserAuthGradeEnum.NORMAL;

    protected UserAuthEntity() {
    }

    private UserAuthEntity(Builder builder) {
        this.clientId = builder.clientId;
        this.clientSecret = builder.clientSecret;
        this.authStatus = builder.authStatus;
        this.expirationDate = builder.expirationDate;
        this.userAuthGrade = (builder.userAuthGrade == null) ? UserAuthGradeEnum.NORMAL : builder.userAuthGrade;
    }

    public static class Builder {
        private String clientId;
        private String clientSecret;
        private AuthStatusEnum authStatus;
        private LocalDateTime expirationDate;
        private UserAuthGradeEnum userAuthGrade;

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public Builder authStatus(AuthStatusEnum authStatus) {
            this.authStatus = authStatus;
            return this;
        }

        public Builder expirationDate(LocalDateTime expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public Builder userAuthGrade(UserAuthGradeEnum userAuthGrade) {
            this.userAuthGrade = userAuthGrade;
            return this;
        }

        public UserAuthEntity build() {
            return new UserAuthEntity(this);
        }
    }
}
