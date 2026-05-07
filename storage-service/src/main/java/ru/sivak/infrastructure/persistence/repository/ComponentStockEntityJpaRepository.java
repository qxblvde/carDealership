package ru.sivak.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sivak.infrastructure.persistence.entity.ComponentStockEntity;

import java.util.Optional;
import java.util.UUID;

public interface ComponentStockEntityJpaRepository extends JpaRepository<ComponentStockEntity, UUID> {
    Optional<ComponentStockEntity> findByIdAndRemovedFalse(UUID id);
    Optional<ComponentStockEntity> findByComponentIdAndRemovedFalse(UUID componentId);
}
