package ru.sivak.infrastructure.repositories.jpa;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sivak.application.query.CarQuery;
import ru.sivak.domain.entities.Car;
import ru.sivak.domain.repositories.CarRepository;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.persistence.entity.*;
import ru.sivak.infrastructure.persistence.repository.CarEntityJpaRepository;
import ru.sivak.infrastructure.persistence.specification.CarEntitySpecifications;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaCarRepository implements CarRepository {
    private final CarEntityJpaRepository repository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void create(Car entity) {
        if (repository.existsById(entity.getId().getId())) {
            throw new IllegalArgumentException("Car already exists");
        }
        CarEntity persisted = new CarEntity();
        JpaDomainMapper.mapToEntity(
                entity,
                persisted,
                entityManager.getReference(BodyEntity.class, entity.getBodyType().getId().getId()),
                entityManager.getReference(BrandEntity.class, entity.getBrandName().getId().getId()),
                entityManager.getReference(ColorEntity.class, entity.getColor().getId().getId()),
                entityManager.getReference(DriveEntity.class, entity.getDriveType().getId().getId()),
                entityManager.getReference(EngineEntity.class, entity.getEngine().getId().getId()),
                entityManager.getReference(FuelEntity.class, entity.getFuel().getId().getId()),
                entityManager.getReference(CarModelEntity.class, entity.getModel().getId().getId()),
                entityManager.getReference(TransmissionEntity.class, entity.getTransmission().getId().getId()),
                entityManager.getReference(SteeringEntity.class, entity.getSteering().getId().getId()),
                entityManager.getReference(WheelEntity.class, entity.getWheel().getId().getId()),
                entityManager.getReference(InteriorEntity.class, entity.getInterior().getId().getId())
        );
        repository.save(persisted);
    }

    @Override
    @Transactional
    public void update(Car entity) {
        CarEntity persisted = repository.findByIdAndRemovedFalse(entity.getId().getId())
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));
        JpaDomainMapper.mapToEntity(
                entity,
                persisted,
                entityManager.getReference(BodyEntity.class, entity.getBodyType().getId().getId()),
                entityManager.getReference(BrandEntity.class, entity.getBrandName().getId().getId()),
                entityManager.getReference(ColorEntity.class, entity.getColor().getId().getId()),
                entityManager.getReference(DriveEntity.class, entity.getDriveType().getId().getId()),
                entityManager.getReference(EngineEntity.class, entity.getEngine().getId().getId()),
                entityManager.getReference(FuelEntity.class, entity.getFuel().getId().getId()),
                entityManager.getReference(CarModelEntity.class, entity.getModel().getId().getId()),
                entityManager.getReference(TransmissionEntity.class, entity.getTransmission().getId().getId()),
                entityManager.getReference(SteeringEntity.class, entity.getSteering().getId().getId()),
                entityManager.getReference(WheelEntity.class, entity.getWheel().getId().getId()),
                entityManager.getReference(InteriorEntity.class, entity.getInterior().getId().getId())
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
    public Optional<Car> find(Id id) {
        return repository.findByIdAndRemovedFalse(id.getId()).map(JpaDomainMapper::toDomain);
    }

    @Override
    public List<Car> findAll() {
        return repository.findAllByRemovedFalse().stream().map(JpaDomainMapper::toDomain).toList();
    }

    @Override
    public List<Car> query(CarQuery query) {
        return repository.findAll(CarEntitySpecifications.byQuery(query))
                .stream()
                .map(JpaDomainMapper::toDomain)
                .toList();
    }
}
