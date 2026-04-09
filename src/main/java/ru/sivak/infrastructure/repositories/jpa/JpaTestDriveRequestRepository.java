package ru.sivak.infrastructure.repositories.jpa;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sivak.application.query.TestDriveRequestQuery;
import ru.sivak.domain.entities.TestDriveRequest;
import ru.sivak.domain.repositories.TestDriveRequestRepository;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.persistence.entity.AppUserEntity;
import ru.sivak.infrastructure.persistence.entity.CarEntity;
import ru.sivak.infrastructure.persistence.entity.TestDriveRequestEntity;
import ru.sivak.infrastructure.persistence.repository.TestDriveRequestEntityJpaRepository;
import ru.sivak.infrastructure.persistence.specification.TestDriveRequestEntitySpecifications;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaTestDriveRequestRepository implements TestDriveRequestRepository {
    private final TestDriveRequestEntityJpaRepository repository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void create(TestDriveRequest entity) {
        if (repository.existsById(entity.getId().getId())) {
            throw new IllegalArgumentException("TestDriveRequest already exists");
        }
        TestDriveRequestEntity persisted = new TestDriveRequestEntity();
        JpaDomainMapper.mapToEntity(
                entity,
                persisted,
                entityManager.getReference(AppUserEntity.class, entity.getClientId().getId()),
                entityManager.getReference(CarEntity.class, entity.getCarId().getId())
        );
        repository.save(persisted);
    }

    @Override
    @Transactional
    public void update(TestDriveRequest entity) {
        TestDriveRequestEntity persisted = repository.findByIdAndRemovedFalse(entity.getId().getId())
                .orElseThrow(() -> new IllegalArgumentException("TestDriveRequest not found"));
        JpaDomainMapper.mapToEntity(
                entity,
                persisted,
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
    public Optional<TestDriveRequest> find(Id id) {
        return repository.findByIdAndRemovedFalse(id.getId()).map(JpaDomainMapper::toDomain);
    }

    @Override
    public List<TestDriveRequest> findAll() {
        return repository.findAllByRemovedFalse().stream().map(JpaDomainMapper::toDomain).toList();
    }

    @Override
    public List<TestDriveRequest> query(TestDriveRequestQuery query) {
        return repository.findAll(TestDriveRequestEntitySpecifications.byQuery(query)).stream()
                .map(JpaDomainMapper::toDomain)
                .toList();
    }
}
