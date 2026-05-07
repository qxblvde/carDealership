package ru.sivak.domain.repositories;

import ru.sivak.domain.entities.CarStock;

import java.util.Optional;
import java.util.UUID;

public interface CarStockRepository extends Repository<CarStock> {
    Optional<CarStock> findByCarId(UUID carId);
}
