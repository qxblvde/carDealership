package ru.sivak.infrastructure.repositories.jpa;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sivak.application.query.CustomOrderQuery;
import ru.sivak.domain.order.custom.CustomOrder;
import ru.sivak.domain.repositories.CustomOrderRepository;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.persistence.entity.AppUserEntity;
import ru.sivak.infrastructure.persistence.entity.CustomOrderEntity;
import ru.sivak.infrastructure.persistence.repository.CustomOrderEntityJpaRepository;
import ru.sivak.infrastructure.persistence.specification.CustomOrderEntitySpecifications;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaCustomOrderRepository implements CustomOrderRepository {
    private final CustomOrderEntityJpaRepository repository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void create(CustomOrder entity) {
        if (repository.existsById(entity.getId().getId())) {
            throw new IllegalArgumentException("CustomOrder already exists");
        }
        CustomOrderEntity persisted = new CustomOrderEntity();
        JpaDomainMapper.mapToEntity(
                entity,
                persisted,
                entityManager.getReference(AppUserEntity.class, entity.getManagerId().getId()),
                entityManager.getReference(AppUserEntity.class, entity.getClientId().getId())
        );
        repository.save(persisted);
    }

    @Override
    @Transactional
    public void update(CustomOrder entity) {
        CustomOrderEntity persisted = repository.findByIdAndRemovedFalse(entity.getId().getId())
                .orElseThrow(() -> new IllegalArgumentException("CustomOrder not found"));
        JpaDomainMapper.mapToEntity(
                entity,
                persisted,
                entityManager.getReference(AppUserEntity.class, entity.getManagerId().getId()),
                entityManager.getReference(AppUserEntity.class, entity.getClientId().getId())
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
    public Optional<CustomOrder> find(Id id) {
        return repository.findByIdAndRemovedFalse(id.getId()).map(JpaDomainMapper::toDomain);
    }

    @Override
    public List<CustomOrder> findAll() {
        return repository.findAllByRemovedFalse().stream().map(JpaDomainMapper::toDomain).toList();
    }

    @Override
    public List<CustomOrder> query(CustomOrderQuery query) {
        return repository.findAll(CustomOrderEntitySpecifications.byQuery(query)).stream()
                .map(JpaDomainMapper::toDomain)
                .toList();
    }
}
