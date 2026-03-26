package ru.sivak.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.sivak.infrastructure.persistence.entity.CarEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarEntityJpaRepository extends JpaRepository<CarEntity, UUID>, JpaSpecificationExecutor<CarEntity> {
    Optional<CarEntity> findByIdAndRemovedFalse(UUID id);

    List<CarEntity> findAllByRemovedFalse();
}
