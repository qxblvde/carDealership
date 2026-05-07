package ru.sivak.infrastructure.repositories.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sivak.domain.entities.AssemblyOrder;
import ru.sivak.domain.repositories.AssemblyOrderRepository;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.persistence.entity.AssemblyOrderEntity;
import ru.sivak.infrastructure.persistence.repository.AssemblyOrderEntityJpaRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaAssemblyOrderRepository implements AssemblyOrderRepository {

    private final AssemblyOrderEntityJpaRepository repository;

    @Override
    @Transactional
    public void create(AssemblyOrder assemblyOrder) {
        if (repository.existsById(assemblyOrder.getId().getId())) {
            throw new IllegalArgumentException("AssemblyOrder already exists");
        }
        AssemblyOrderEntity entity = new AssemblyOrderEntity();
        mapToEntity(assemblyOrder, entity);
        repository.save(entity);
    }

    @Override
    @Transactional
    public void update(AssemblyOrder assemblyOrder) {
        AssemblyOrderEntity entity = repository.findByIdAndRemovedFalse(assemblyOrder.getId().getId())
                .orElseThrow(() -> new IllegalArgumentException("AssemblyOrder not found"));
        mapToEntity(assemblyOrder, entity);
        repository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Id id) {
        repository.findByIdAndRemovedFalse(id.getId()).ifPresent(entity -> {
            entity.setRemoved(true);
            repository.save(entity);
        });
    }

    @Override
    public Optional<AssemblyOrder> find(Id id) {
        return repository.findByIdAndRemovedFalse(id.getId()).map(this::toDomain);
    }

    @Override
    public Optional<AssemblyOrder> findBySourceOrderId(UUID sourceOrderId) {
        return repository.findBySourceOrderIdAndRemovedFalse(sourceOrderId).map(this::toDomain);
    }

    @Override
    public List<AssemblyOrder> findAll() {
        return repository.findAllByRemovedFalse().stream().map(this::toDomain).toList();
    }

    private AssemblyOrder toDomain(AssemblyOrderEntity entity) {
        AssemblyOrder order = new AssemblyOrder(
                Id.of(entity.getId()),
                entity.getSourceOrderId(),
                entity.getSourceOrderType(),
                entity.getCarId(),
                entity.getWarehouseEmployeeId(),
                entity.getRequiredComponentIds() != null ? entity.getRequiredComponentIds() : new HashSet<>()
        );
        order.updateStatus(AssemblyOrder.Status.valueOf(entity.getStatus()));
        return order;
    }

    private void mapToEntity(AssemblyOrder source, AssemblyOrderEntity target) {
        target.setId(source.getId().getId());
        target.setSourceOrderId(source.getSourceOrderId());
        target.setSourceOrderType(source.getSourceOrderType());
        target.setCarId(source.getCarId());
        target.setWarehouseEmployeeId(source.getWarehouseEmployeeId());
        target.setRequiredComponentIds(source.getRequiredComponentIds());
        target.setStatus(source.getStatus().name());
    }
}
