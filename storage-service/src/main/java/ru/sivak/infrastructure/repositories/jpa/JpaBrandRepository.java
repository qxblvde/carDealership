package ru.sivak.infrastructure.repositories.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sivak.application.query.BrandQuery;
import ru.sivak.domain.entities.Brand;
import ru.sivak.domain.repositories.BrandRepository;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.persistence.entity.BrandEntity;
import ru.sivak.infrastructure.persistence.repository.BrandEntityJpaRepository;
import ru.sivak.infrastructure.persistence.repository.CarModelEntityJpaRepository;
import ru.sivak.infrastructure.persistence.specification.ComponentEntitySpecifications;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaBrandRepository implements BrandRepository {
    private final BrandEntityJpaRepository repository;
    private final CarModelEntityJpaRepository modelRepository;

    @Override
    @Transactional
    public void create(Brand entity) {
        if (repository.existsById(entity.getId().getId())) {
            throw new IllegalArgumentException("Brand already exists");
        }
        BrandEntity persisted = new BrandEntity();
        JpaDomainMapper.mapToEntity(
                entity,
                persisted,
                JpaRepositorySupport.resolveModelEntities(modelRepository, entity.getSuitableModels())
        );
        repository.save(persisted);
    }

    @Override
    @Transactional
    public void update(Brand entity) {
        BrandEntity persisted = repository.findByIdAndRemovedFalse(entity.getId().getId())
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        JpaDomainMapper.mapToEntity(
                entity,
                persisted,
                JpaRepositorySupport.resolveModelEntities(modelRepository, entity.getSuitableModels())
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
    public Optional<Brand> find(Id id) {
        return repository.findByIdAndRemovedFalse(id.getId()).map(JpaDomainMapper::toDomain);
    }

    @Override
    public List<Brand> findAll() {
        return repository.findAllByRemovedFalse().stream().map(JpaDomainMapper::toDomain).toList();
    }

    @Override
    public List<Brand> query(BrandQuery query) {
        return repository.findAll(ComponentEntitySpecifications.brandByQuery(query)).stream()
                .map(JpaDomainMapper::toDomain)
                .toList();
    }
}
