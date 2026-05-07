package ru.sivak.domain.repositories;

import ru.sivak.application.query.CarQuery;
import ru.sivak.domain.entities.Car;

import java.util.List;

public interface CarRepository extends Repository<Car> {
    List<Car> query(CarQuery query);
}
