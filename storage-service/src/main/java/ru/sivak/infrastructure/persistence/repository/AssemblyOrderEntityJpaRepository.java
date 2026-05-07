package ru.sivak.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sivak.infrastructure.persistence.entity.AssemblyOrderEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssemblyOrderEntityJpaRepository extends JpaRepository<AssemblyOrderEntity, UUID> {
    Optional<AssemblyOrderEntity> findByIdAndRemovedFalse(UUID id);
    Optional<AssemblyOrderEntity> findBySourceOrderIdAndRemovedFalse(UUID sourceOrderId);
    List<AssemblyOrderEntity> findAllByRemovedFalse();
}
