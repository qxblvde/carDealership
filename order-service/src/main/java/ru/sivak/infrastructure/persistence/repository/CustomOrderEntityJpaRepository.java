package ru.sivak.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.sivak.infrastructure.persistence.entity.CustomOrderEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomOrderEntityJpaRepository extends JpaRepository<CustomOrderEntity, UUID>, JpaSpecificationExecutor<CustomOrderEntity> {
    Optional<CustomOrderEntity> findByIdAndRemovedFalse(UUID id);

    List<CustomOrderEntity> findAllByRemovedFalse();
}
