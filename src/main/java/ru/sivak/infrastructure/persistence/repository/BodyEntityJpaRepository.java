package ru.sivak.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.sivak.infrastructure.persistence.entity.BodyEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BodyEntityJpaRepository extends JpaRepository<BodyEntity, UUID>, JpaSpecificationExecutor<BodyEntity> {
    Optional<BodyEntity> findByIdAndRemovedFalse(UUID id);

    List<BodyEntity> findAllByRemovedFalse();
}
