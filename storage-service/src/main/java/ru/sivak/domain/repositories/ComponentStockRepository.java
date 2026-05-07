package ru.sivak.domain.repositories;

import ru.sivak.domain.entities.ComponentStock;

import java.util.Optional;
import java.util.UUID;

public interface ComponentStockRepository extends Repository<ComponentStock> {
    Optional<ComponentStock> findByComponentId(UUID componentId);
}
