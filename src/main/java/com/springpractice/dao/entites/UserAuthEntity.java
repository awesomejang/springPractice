package com.springpractice.dao.entites;

import com.springpractice.dao.converter.UserAuthGradeConverter;
import com.springpractice.dao.enums.AuthStatusEnum;
import com.springpractice.dao.enums.UserAuthGradeEnum;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "userAuthEntity", fetch = FetchType.LAZY)
    private final Set<UserMenuMappingEntity> userMenuMappingEntities = new HashSet<>();

    protected UserAuthEntity() {
    }

    private UserAuthEntity(Builder builder) {
        this.clientId = builder.clientId;
        this.clientSecret = builder.clientSecret;
        this.authStatus = (builder.authStatus == null) ? AuthStatusEnum.INACTIVE : builder.authStatus;
        this.expirationDate = (builder.expirationDate == null) ? LocalDateTime.now() : builder.expirationDate;
        this.userAuthGrade = (builder.userAuthGrade == null) ? UserAuthGradeEnum.NORMAL : builder.userAuthGrade;
    }

    public static class Builder {
        private String clientId;
        private String clientSecret;
        private AuthStatusEnum authStatus;
        private LocalDateTime expirationDate;
        private UserAuthGradeEnum userAuthGrade;

        // 이렇게 하면 해결은 되겠지만 빌더 패턴의 장점을 활용하지 못한다.
        // 최대한 엔티티 생성자에서 해결하는게 좋을듯 물론 초기화 값이 필수여야 한다면 어쩔수 없을것같긴하다.
//        public Builder(LocalDateTime expirationDate) {
//            this.expirationDate = expirationDate;
//        }

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
