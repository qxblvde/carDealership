package ru.sivak.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.sivak.infrastructure.persistence.entity.FuelEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FuelEntityJpaRepository extends JpaRepository<FuelEntity, UUID>, JpaSpecificationExecutor<FuelEntity> {
    Optional<FuelEntity> findByIdAndRemovedFalse(UUID id);

    List<FuelEntity> findAllByRemovedFalse();
}
