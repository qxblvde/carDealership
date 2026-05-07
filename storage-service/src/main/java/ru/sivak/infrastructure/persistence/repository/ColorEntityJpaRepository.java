package ru.sivak.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.sivak.infrastructure.persistence.entity.ColorEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ColorEntityJpaRepository extends JpaRepository<ColorEntity, UUID>, JpaSpecificationExecutor<ColorEntity> {
    Optional<ColorEntity> findByIdAndRemovedFalse(UUID id);

    List<ColorEntity> findAllByRemovedFalse();
}
