package ru.sivak.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.sivak.infrastructure.persistence.entity.BrandEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BrandEntityJpaRepository extends JpaRepository<BrandEntity, UUID>, JpaSpecificationExecutor<BrandEntity> {
    Optional<BrandEntity> findByIdAndRemovedFalse(UUID id);

    List<BrandEntity> findAllByRemovedFalse();
}
