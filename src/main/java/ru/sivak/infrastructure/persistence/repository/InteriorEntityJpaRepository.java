package ru.sivak.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.sivak.infrastructure.persistence.entity.InteriorEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InteriorEntityJpaRepository extends JpaRepository<InteriorEntity, UUID>, JpaSpecificationExecutor<InteriorEntity> {
    Optional<InteriorEntity> findByIdAndRemovedFalse(UUID id);

    List<InteriorEntity> findAllByRemovedFalse();
}
