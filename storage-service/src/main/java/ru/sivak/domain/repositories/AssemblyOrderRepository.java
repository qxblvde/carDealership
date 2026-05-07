package ru.sivak.domain.repositories;

import ru.sivak.domain.entities.AssemblyOrder;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssemblyOrderRepository {
    void create(AssemblyOrder assemblyOrder);
    void update(AssemblyOrder assemblyOrder);
    void delete(Id id);
    Optional<AssemblyOrder> find(Id id);
    Optional<AssemblyOrder> findBySourceOrderId(UUID sourceOrderId);
    List<AssemblyOrder> findAll();
}
