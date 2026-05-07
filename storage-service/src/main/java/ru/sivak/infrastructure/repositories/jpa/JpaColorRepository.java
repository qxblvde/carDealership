package ru.sivak.infrastructure.repositories.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sivak.application.query.ColorQuery;
import ru.sivak.domain.entities.Color;
import ru.sivak.domain.repositories.ColorRepository;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.persistence.entity.ColorEntity;
import ru.sivak.infrastructure.persistence.repository.CarModelEntityJpaRepository;
import ru.sivak.infrastructure.persistence.repository.ColorEntityJpaRepository;
import ru.sivak.infrastructure.persistence.specification.ComponentEntitySpecifications;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaColorRepository implements ColorRepository {
    private final ColorEntityJpaRepository repository;
    private final CarModelEntityJpaRepository modelRepository;

    @Override
    @Transactional
    public void create(Color entity) {
        if (repository.existsById(entity.getId().getId())) {
            throw new IllegalArgumentException("Color already exists");
        }
        ColorEntity persisted = new ColorEntity();
        JpaDomainMapper.mapToEntity(
                entity,
                persisted,
                JpaRepositorySupport.resolveModelEntities(modelRepository, entity.getSuitableModels())
        );
        repository.save(persisted);
    }

    @Override
    @Transactional
    public void update(Color entity) {
        ColorEntity persisted = repository.findByIdAndRemovedFalse(entity.getId().getId())
                .orElseThrow(() -> new IllegalArgumentException("Color not found"));
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
    public Optional<Color> find(Id id) {
        return repository.findByIdAndRemovedFalse(id.getId()).map(JpaDomainMapper::toDomain);
    }

    @Override
    public List<Color> findAll() {
        return repository.findAllByRemovedFalse().stream().map(JpaDomainMapper::toDomain).toList();
    }

    @Override
    public List<Color> query(ColorQuery query) {
        return repository.findAll(ComponentEntitySpecifications.colorByQuery(query)).stream()
                .map(JpaDomainMapper::toDomain)
                .toList();
    }
}
