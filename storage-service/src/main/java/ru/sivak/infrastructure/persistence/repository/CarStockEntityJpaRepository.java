package ru.sivak.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sivak.infrastructure.persistence.entity.CarStockEntity;

import java.util.Optional;
import java.util.UUID;

public interface CarStockEntityJpaRepository extends JpaRepository<CarStockEntity, UUID> {
    Optional<CarStockEntity> findByIdAndRemovedFalse(UUID id);
    Optional<CarStockEntity> findByCarIdAndRemovedFalse(UUID carId);
}
