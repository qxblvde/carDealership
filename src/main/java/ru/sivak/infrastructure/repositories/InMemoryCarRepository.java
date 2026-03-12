package ru.sivak.infrastructure.repositories;

import ru.sivak.application.query.CarQuery;
import ru.sivak.domain.entities.Car;
import ru.sivak.domain.repositories.CarRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryCarRepository implements CarRepository {
    private final Map<Id, Car> storage = new HashMap<>();

    @Override
    public void save(Car entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public void delete(Id id) {
        storage.remove(id);
    }

    @Override
    public Optional<Car> find(Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Car> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Car> query(CarQuery query) {
        return storage.values().stream()
                .filter(car -> query.getBrandName() == null ||
                        car.getBrandName().getName().equals(query.getBrandName()))
                .filter(car -> query.getModelName() == null ||
                        car.getModel().getModelName().equals(query.getModelName()))
                .filter(car -> query.getBodyType() == null ||
                        car.getBodyType().getBodyType().equals(query.getBodyType()))
                .filter(car -> query.getColor() == null ||
                        car.getColor().getColor().equals(query.getColor()))
                .filter(car -> query.getDriveType() == null ||
                        car.getDriveType().getDriveType().equals(query.getDriveType()))
                .filter(car -> query.getEnginePower() == null ||
                        car.getEngine().getPower().equals(query.getEnginePower()))
                .filter(car -> query.getEngineVolume() == null ||
                        car.getEngine().getVolume().equals(query.getEngineVolume()))
                .filter(car -> query.getFuelType() == null ||
                        car.getFuel().getFuelType().equals(query.getFuelType()))
                .filter(car -> query.getMinPrice() == null ||
                        car.getPrice().getAmount()
                                .compareTo(query.getMinPrice().getAmount()) >= 0)
                .filter(car -> query.getMaxPrice() == null ||
                        car.getPrice().getAmount()
                                .compareTo(query.getMaxPrice().getAmount()) <= 0)
                .collect(Collectors.toList());
    }
}
