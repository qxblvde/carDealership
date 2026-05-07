package ru.sivak.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sivak.infrastructure.persistence.entity.OutboxEventEntity;

import java.util.List;
import java.util.UUID;

public interface OutboxEventEntityJpaRepository extends JpaRepository<OutboxEventEntity, UUID> {
    List<OutboxEventEntity> findAllByPublishedFalseOrderByCreatedAtAsc();
}
