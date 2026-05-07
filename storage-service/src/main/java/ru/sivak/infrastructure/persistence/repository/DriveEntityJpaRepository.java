package ru.sivak.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.sivak.infrastructure.persistence.entity.DriveEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DriveEntityJpaRepository extends JpaRepository<DriveEntity, UUID>, JpaSpecificationExecutor<DriveEntity> {
    Optional<DriveEntity> findByIdAndRemovedFalse(UUID id);

    List<DriveEntity> findAllByRemovedFalse();
}
