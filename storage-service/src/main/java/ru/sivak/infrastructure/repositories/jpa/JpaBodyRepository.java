package ru.sivak.infrastructure.repositories.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sivak.application.query.BodyQuery;
import ru.sivak.domain.entities.Body;
import ru.sivak.domain.repositories.BodyRepository;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.persistence.entity.BodyEntity;
import ru.sivak.infrastructure.persistence.repository.BodyEntityJpaRepository;
import ru.sivak.infrastructure.persistence.repository.CarModelEntityJpaRepository;
import ru.sivak.infrastructure.persistence.specification.ComponentEntitySpecifications;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaBodyRepository implements BodyRepository {
    private final BodyEntityJpaRepository repository;
    private final CarModelEntityJpaRepository modelRepository;

    @Override
    @Transactional
    public void create(Body entity) {
        if (repository.existsById(entity.getId().getId())) {
            throw new IllegalArgumentException("Body already exists");
        }
        BodyEntity persisted = new BodyEntity();
        JpaDomainMapper.mapToEntity(
                entity,
                persisted,
                JpaRepositorySupport.resolveModelEntities(modelRepository, entity.getSuitableModels())
        );
        repository.save(persisted);
    }

    @Override
    @Transactional
    public void update(Body entity) {
        BodyEntity persisted = repository.findByIdAndRemovedFalse(entity.getId().getId())
                .orElseThrow(() -> new IllegalArgumentException("Body not found"));
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
    public Optional<Body> find(Id id) {
        return repository.findByIdAndRemovedFalse(id.getId()).map(JpaDomainMapper::toDomain);
    }

    @Override
    public List<Body> findAll() {
        return repository.findAllByRemovedFalse().stream().map(JpaDomainMapper::toDomain).toList();
    }

    @Override
    public List<Body> query(BodyQuery query) {
        return repository.findAll(ComponentEntitySpecifications.bodyByQuery(query)).stream()
                .map(JpaDomainMapper::toDomain)
                .toList();
    }
}
