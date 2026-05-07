package ru.sivak.application.services;

import ru.sivak.application.dto.AssemblyOrderDto;
import ru.sivak.domain.entities.AssemblyOrder;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IAssemblyOrderService {
    AssemblyOrderDto create(String sourceOrderType, UUID sourceOrderId, UUID carId, UUID warehouseEmployeeId, Set<UUID> requiredComponentIds);
    AssemblyOrderDto get(Id id);
    List<AssemblyOrderDto> findAll();
    AssemblyOrderDto update(Id id, AssemblyOrder.Status newStatus);
    void delete(Id id);
}
