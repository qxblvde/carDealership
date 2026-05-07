package ru.sivak.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.sivak.infrastructure.persistence.entity.EngineEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EngineEntityJpaRepository extends JpaRepository<EngineEntity, UUID>, JpaSpecificationExecutor<EngineEntity> {
    Optional<EngineEntity> findByIdAndRemovedFalse(UUID id);

    List<EngineEntity> findAllByRemovedFalse();
}
