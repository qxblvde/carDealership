package ru.sivak.infrastructure.repositories.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sivak.application.query.DriveQuery;
import ru.sivak.domain.entities.Drive;
import ru.sivak.domain.repositories.DriveRepository;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.persistence.entity.DriveEntity;
import ru.sivak.infrastructure.persistence.repository.CarModelEntityJpaRepository;
import ru.sivak.infrastructure.persistence.repository.DriveEntityJpaRepository;
import ru.sivak.infrastructure.persistence.specification.ComponentEntitySpecifications;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaDriveRepository implements DriveRepository {
    private final DriveEntityJpaRepository repository;
    private final CarModelEntityJpaRepository modelRepository;

    @Override
    @Transactional
    public void create(Drive entity) {
        if (repository.existsById(entity.getId().getId())) {
            throw new IllegalArgumentException("Drive already exists");
        }
        DriveEntity persisted = new DriveEntity();
        JpaDomainMapper.mapToEntity(
                entity,
                persisted,
                JpaRepositorySupport.resolveModelEntities(modelRepository, entity.getSuitableModels())
        );
        repository.save(persisted);
    }

    @Override
    @Transactional
    public void update(Drive entity) {
        DriveEntity persisted = repository.findByIdAndRemovedFalse(entity.getId().getId())
                .orElseThrow(() -> new IllegalArgumentException("Drive not found"));
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
    public Optional<Drive> find(Id id) {
        return repository.findByIdAndRemovedFalse(id.getId()).map(JpaDomainMapper::toDomain);
    }

    @Override
    public List<Drive> findAll() {
        return repository.findAllByRemovedFalse().stream().map(JpaDomainMapper::toDomain).toList();
    }

    @Override
    public List<Drive> query(DriveQuery query) {
        return repository.findAll(ComponentEntitySpecifications.driveByQuery(query)).stream()
                .map(JpaDomainMapper::toDomain)
                .toList();
    }
}
