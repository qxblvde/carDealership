package ru.sivak.infrastructure.repositories.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sivak.domain.entities.ComponentStock;
import ru.sivak.domain.repositories.ComponentStockRepository;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.persistence.entity.ComponentStockEntity;
import ru.sivak.infrastructure.persistence.repository.ComponentStockEntityJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaComponentStockRepository implements ComponentStockRepository {

    private final ComponentStockEntityJpaRepository repository;

    @Override
    @Transactional
    public void create(ComponentStock entity) {
        if (repository.existsById(entity.getId().getId())) {
            throw new IllegalArgumentException("ComponentStock already exists");
        }
        ComponentStockEntity persisted = new ComponentStockEntity();
        mapToEntity(entity, persisted);
        repository.save(persisted);
    }

    @Override
    @Transactional
    public void update(ComponentStock entity) {
        ComponentStockEntity persisted = repository.findByIdAndRemovedFalse(entity.getId().getId())
                .orElseThrow(() -> new IllegalArgumentException("ComponentStock not found"));
        mapToEntity(entity, persisted);
        repository.save(persisted);
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
    public Optional<ComponentStock> find(Id id) {
        return repository.findByIdAndRemovedFalse(id.getId()).map(this::toDomain);
    }

    @Override
    public Optional<ComponentStock> findByComponentId(UUID componentId) {
        return repository.findByComponentIdAndRemovedFalse(componentId).map(this::toDomain);
    }

    @Override
    public List<ComponentStock> findAll() {
        return repository.findAll().stream()
                .filter(e -> !e.isRemoved())
                .map(this::toDomain)
                .toList();
    }

    private ComponentStock toDomain(ComponentStockEntity entity) {
        return new ComponentStock(
                Id.of(entity.getId()),
                entity.getComponentId(),
                entity.getQuantity(),
                entity.getReserved()
        );
    }

    private void mapToEntity(ComponentStock source, ComponentStockEntity target) {
        target.setId(source.getId().getId());
        target.setComponentId(source.getComponentId());
        target.setQuantity(source.getQuantity());
        target.setReserved(source.getReserved());
    }
}
