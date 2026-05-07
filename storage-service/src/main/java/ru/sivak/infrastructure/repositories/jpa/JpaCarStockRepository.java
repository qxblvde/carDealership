package ru.sivak.infrastructure.repositories.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sivak.domain.entities.CarStock;
import ru.sivak.domain.repositories.CarStockRepository;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.persistence.entity.CarStockEntity;
import ru.sivak.infrastructure.persistence.repository.CarStockEntityJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JpaCarStockRepository implements CarStockRepository {

    private final CarStockEntityJpaRepository repository;

    @Override
    @Transactional
    public void create(CarStock entity) {
        if (repository.existsById(entity.getId().getId())) {
            throw new IllegalArgumentException("CarStock already exists");
        }
        CarStockEntity persisted = new CarStockEntity();
        mapToEntity(entity, persisted);
        repository.save(persisted);
    }

    @Override
    @Transactional
    public void update(CarStock entity) {
        CarStockEntity persisted = repository.findByIdAndRemovedFalse(entity.getId().getId())
                .orElseThrow(() -> new IllegalArgumentException("CarStock not found"));
        mapToEntity(entity, persisted);
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
    public Optional<CarStock> find(Id id) {
        return repository.findByIdAndRemovedFalse(id.getId()).map(this::toDomain);
    }

    @Override
    public Optional<CarStock> findByCarId(UUID carId) {
        return repository.findByCarIdAndRemovedFalse(carId).map(this::toDomain);
    }

    @Override
    public List<CarStock> findAll() {
        return repository.findAll().stream()
                .filter(e -> !e.isRemoved())
                .map(this::toDomain)
                .toList();
    }

    private CarStock toDomain(CarStockEntity entity) {
        return new CarStock(
                Id.of(entity.getId()),
                entity.getCarId(),
                entity.getQuantity(),
                entity.getReserved()
        );
    }

    private void mapToEntity(CarStock source, CarStockEntity target) {
        target.setId(source.getId().getId());
        target.setCarId(source.getCarId());
        target.setQuantity(source.getQuantity());
        target.setReserved(source.getReserved());
    }
}
