package ru.sivak.infrastructure.repositories.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sivak.application.query.WheelQuery;
import ru.sivak.domain.entities.Wheel;
import ru.sivak.domain.repositories.WheelRepository;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.persistence.entity.WheelEntity;
import ru.sivak.infrastructure.persistence.repository.CarModelEntityJpaRepository;
import ru.sivak.infrastructure.persistence.repository.WheelEntityJpaRepository;
import ru.sivak.infrastructure.persistence.specification.ComponentEntitySpecifications;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaWheelRepository implements WheelRepository {
    private final WheelEntityJpaRepository repository;
    private final CarModelEntityJpaRepository modelRepository;

    @Override
    @Transactional
    public void create(Wheel entity) {
        if (repository.existsById(entity.getId().getId())) {
            throw new IllegalArgumentException("Wheel already exists");
        }
        WheelEntity persisted = new WheelEntity();
        JpaDomainMapper.mapToEntity(
                entity,
                persisted,
                JpaRepositorySupport.resolveModelEntities(modelRepository, entity.getSuitableModels())
        );
        repository.save(persisted);
    }

    @Override
    @Transactional
    public void update(Wheel entity) {
        WheelEntity persisted = repository.findByIdAndRemovedFalse(entity.getId().getId())
                .orElseThrow(() -> new IllegalArgumentException("Wheel not found"));
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
    public Optional<Wheel> find(Id id) {
        return repository.findByIdAndRemovedFalse(id.getId()).map(JpaDomainMapper::toDomain);
    }

    @Override
    public List<Wheel> findAll() {
        return repository.findAllByRemovedFalse().stream().map(JpaDomainMapper::toDomain).toList();
    }

    @Override
    public List<Wheel> query(WheelQuery query) {
        return repository.findAll(ComponentEntitySpecifications.wheelByQuery(query)).stream()
                .map(JpaDomainMapper::toDomain)
                .toList();
    }
}
