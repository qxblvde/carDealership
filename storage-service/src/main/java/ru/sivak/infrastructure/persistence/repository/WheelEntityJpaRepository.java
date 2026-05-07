package ru.sivak.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.sivak.infrastructure.persistence.entity.WheelEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WheelEntityJpaRepository extends JpaRepository<WheelEntity, UUID>, JpaSpecificationExecutor<WheelEntity> {
    Optional<WheelEntity> findByIdAndRemovedFalse(UUID id);

    List<WheelEntity> findAllByRemovedFalse();
}
