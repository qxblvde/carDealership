package ru.sivak.infrastructure.repositories.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sivak.application.query.ModelQuery;
import ru.sivak.domain.entities.Model;
import ru.sivak.domain.repositories.ModelRepository;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.persistence.entity.CarModelEntity;
import ru.sivak.infrastructure.persistence.repository.CarModelEntityJpaRepository;
import ru.sivak.infrastructure.persistence.specification.CarModelEntitySpecifications;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaModelRepository implements ModelRepository {
    private final CarModelEntityJpaRepository repository;

    @Override
    @Transactional
    public void create(Model entity) {
        if (repository.existsById(entity.getId().getId())) {
            throw new IllegalArgumentException("Model already exists");
        }
        CarModelEntity persisted = new CarModelEntity();
        JpaDomainMapper.mapToEntity(entity, persisted);
        repository.save(persisted);
    }

    @Override
    @Transactional
    public void update(Model entity) {
        CarModelEntity persisted = repository.findByIdAndRemovedFalse(entity.getId().getId())
                .orElseThrow(() -> new IllegalArgumentException("Model not found"));
        JpaDomainMapper.mapToEntity(entity, persisted);
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
    public Optional<Model> find(Id id) {
        return repository.findByIdAndRemovedFalse(id.getId()).map(JpaDomainMapper::toDomain);
    }

    @Override
    public List<Model> findAll() {
        return repository.findAllByRemovedFalse().stream().map(JpaDomainMapper::toDomain).toList();
    }

    @Override
    public List<Model> query(ModelQuery query) {
        return repository.findAll(CarModelEntitySpecifications.byQuery(query)).stream()
                .map(JpaDomainMapper::toDomain)
                .toList();
    }
}
