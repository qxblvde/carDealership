package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.sivak.application.dto.AssemblyOrderDto;
import ru.sivak.application.mappers.AssemblyOrderMapper;
import ru.sivak.application.services.IAssemblyOrderService;
import ru.sivak.domain.entities.AssemblyOrder;
import ru.sivak.domain.repositories.AssemblyOrderRepository;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.persistence.entity.AssemblyOrderEntity;
import ru.sivak.infrastructure.persistence.repository.AssemblyOrderEntityJpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class AssemblyOrderService implements IAssemblyOrderService {

    @NonNull
    private final AssemblyOrderRepository assemblyOrderRepository;
    @NonNull
    private final AssemblyOrderEntityJpaRepository assemblyOrderEntityJpaRepository;
    @NonNull
    private final AssemblyOrderMapper assemblyOrderMapper;

    @Override
    @PreAuthorize("hasRole('WAREHOUSE_ADMIN') or hasRole('ADMIN')")
    public AssemblyOrderDto create(
            @NonNull String sourceOrderType,
            @NonNull UUID sourceOrderId,
            UUID carId,
            UUID warehouseEmployeeId,
            @NonNull Set<UUID> requiredComponentIds
    ) {
        AssemblyOrder order = new AssemblyOrder(
                Id.newId(),
                sourceOrderId,
                sourceOrderType,
                carId,
                warehouseEmployeeId,
                requiredComponentIds
        );
        assemblyOrderRepository.create(order);
        AssemblyOrderEntity entity = assemblyOrderEntityJpaRepository.findByIdAndRemovedFalse(order.getId().getId())
                .orElseThrow();
        return assemblyOrderMapper.mapFromEntity(entity);
    }

    @Override
    @PreAuthorize("hasRole('WAREHOUSE_ADMIN') or hasRole('ADMIN')")
    public AssemblyOrderDto get(@NonNull Id id) {
        return assemblyOrderEntityJpaRepository.findByIdAndRemovedFalse(id.getId())
                .map(assemblyOrderMapper::mapFromEntity)
                .orElseThrow(() -> new IllegalArgumentException("AssemblyOrder not found"));
    }

    @Override
    @PreAuthorize("hasRole('WAREHOUSE_ADMIN') or hasRole('ADMIN')")
    public List<AssemblyOrderDto> findAll() {
        return assemblyOrderEntityJpaRepository.findAllByRemovedFalse()
                .stream()
                .map(assemblyOrderMapper::mapFromEntity)
                .toList();
    }

    @Override
    @PreAuthorize("hasRole('WAREHOUSE_ADMIN') or hasRole('ADMIN')")
    public AssemblyOrderDto update(@NonNull Id id, @NonNull AssemblyOrder.Status newStatus) {
        AssemblyOrder order = assemblyOrderRepository.find(id)
                .orElseThrow(() -> new IllegalArgumentException("AssemblyOrder not found"));
        order.updateStatus(newStatus);
        assemblyOrderRepository.update(order);
        AssemblyOrderEntity entity = assemblyOrderEntityJpaRepository.findByIdAndRemovedFalse(id.getId())
                .orElseThrow();
        return assemblyOrderMapper.mapFromEntity(entity);
    }

    @Override
    @PreAuthorize("hasRole('WAREHOUSE_ADMIN') or hasRole('ADMIN')")
    public void delete(@NonNull Id id) {
        assemblyOrderRepository.delete(id);
    }
}
