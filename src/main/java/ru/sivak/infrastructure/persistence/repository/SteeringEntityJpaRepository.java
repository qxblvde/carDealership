package ru.sivak.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.sivak.infrastructure.persistence.entity.SteeringEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SteeringEntityJpaRepository extends JpaRepository<SteeringEntity, UUID>, JpaSpecificationExecutor<SteeringEntity> {
    Optional<SteeringEntity> findByIdAndRemovedFalse(UUID id);

    List<SteeringEntity> findAllByRemovedFalse();
}
