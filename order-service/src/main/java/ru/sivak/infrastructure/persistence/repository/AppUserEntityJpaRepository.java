package ru.sivak.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sivak.infrastructure.persistence.entity.AppUserEntity;

import java.util.Optional;
import java.util.UUID;

public interface AppUserEntityJpaRepository extends JpaRepository<AppUserEntity, UUID> {
    Optional<AppUserEntity> findByIdAndRemovedFalse(UUID id);
    Optional<AppUserEntity> findByUsernameAndRemovedFalse(String username);
}

