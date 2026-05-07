package ru.sivak.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.sivak.infrastructure.persistence.entity.TestDriveRequestEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestDriveRequestEntityJpaRepository extends JpaRepository<TestDriveRequestEntity, UUID>, JpaSpecificationExecutor<TestDriveRequestEntity> {
    Optional<TestDriveRequestEntity> findByIdAndRemovedFalse(UUID id);

    List<TestDriveRequestEntity> findAllByRemovedFalse();
}
