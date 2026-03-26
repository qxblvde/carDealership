package ru.sivak.infrastructure.repositories.jpa;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sivak.application.query.InStockOrderQuery;
import ru.sivak.domain.order.inStock.InStockOrder;
import ru.sivak.domain.repositories.InStockOrderRepository;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.persistence.entity.AppUserEntity;
import ru.sivak.infrastructure.persistence.entity.CarEntity;
import ru.sivak.infrastructure.persistence.entity.InStockOrderEntity;
import ru.sivak.infrastructure.persistence.repository.InStockOrderEntityJpaRepository;
import ru.sivak.infrastructure.persistence.specification.InStockOrderEntitySpecifications;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaInStockOrderRepository implements InStockOrderRepository {
    private final InStockOrderEntityJpaRepository repository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void create(InStockOrder entity) {
        if (repository.existsById(entity.getId().getId())) {
            throw new IllegalArgumentException("InStockOrder already exists");
        }
        InStockOrderEntity persisted = new InStockOrderEntity();
        JpaDomainMapper.mapToEntity(
                entity,
                persisted,
                entityManager.getReference(AppUserEntity.class, entity.getManagerId().getId()),
                entityManager.getReference(AppUserEntity.class, entity.getClientId().getId()),
                entityManager.getReference(CarEntity.class, entity.getCarId().getId())
        );
        repository.save(persisted);
    }

    @Override
    @Transactional
    public void update(InStockOrder entity) {
        InStockOrderEntity persisted = repository.findByIdAndRemovedFalse(entity.getId().getId())
                .orElseThrow(() -> new IllegalArgumentException("InStockOrder not found"));
        JpaDomainMapper.mapToEntity(
                entity,
                persisted,
                entityManager.getReference(AppUserEntity.class, entity.getManagerId().getId()),
                entityManager.getReference(AppUserEntity.class, entity.getClientId().getId()),
                entityManager.getReference(CarEntity.class, entity.getCarId().getId())
        );
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
    public Optional<InStockOrder> find(Id id) {
        return repository.findByIdAndRemovedFalse(id.getId()).map(JpaDomainMapper::toDomain);
    }

    @Override
    public List<InStockOrder> findAll() {
        return repository.findAllByRemovedFalse().stream().map(JpaDomainMapper::toDomain).toList();
    }

    @Override
    public List<InStockOrder> query(InStockOrderQuery query) {
        return repository.findAll(InStockOrderEntitySpecifications.byQuery(query)).stream()
                .map(JpaDomainMapper::toDomain)
                .toList();
    }
}
