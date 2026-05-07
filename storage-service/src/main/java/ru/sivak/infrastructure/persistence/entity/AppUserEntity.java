package ru.sivak.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "app_users")
@Getter
@Setter
@NoArgsConstructor
public class AppUserEntity extends BaseJpaEntity {
    @Column(name = "username", nullable = false, length = 128, unique = true)
    private String username;

    @Column(name = "user_role", nullable = false, length = 64)
    private String userRole;
}
