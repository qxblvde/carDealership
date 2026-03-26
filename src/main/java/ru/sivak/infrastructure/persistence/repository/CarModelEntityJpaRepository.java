package ru.sivak.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.sivak.infrastructure.persistence.entity.CarModelEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarModelEntityJpaRepository extends JpaRepository<CarModelEntity, UUID>, JpaSpecificationExecutor<CarModelEntity> {
    Optional<CarModelEntity> findByIdAndRemovedFalse(UUID id);

    List<CarModelEntity> findAllByRemovedFalse();

    List<CarModelEntity> findByModelNameInAndRemovedFalse(Collection<String> modelNames);
}
