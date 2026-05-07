package ru.sivak.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.sivak.infrastructure.persistence.entity.TransmissionEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransmissionEntityJpaRepository extends JpaRepository<TransmissionEntity, UUID>, JpaSpecificationExecutor<TransmissionEntity> {
    Optional<TransmissionEntity> findByIdAndRemovedFalse(UUID id);

    List<TransmissionEntity> findAllByRemovedFalse();
}
