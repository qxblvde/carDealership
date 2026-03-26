package ru.sivak.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.sivak.infrastructure.persistence.entity.InStockOrderEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InStockOrderEntityJpaRepository extends JpaRepository<InStockOrderEntity, UUID>, JpaSpecificationExecutor<InStockOrderEntity> {
    Optional<InStockOrderEntity> findByIdAndRemovedFalse(UUID id);

    List<InStockOrderEntity> findAllByRemovedFalse();
}
