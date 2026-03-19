package ru.sivak.application.services;

import ru.sivak.application.dto.CarDto;
import ru.sivak.application.query.CarQuery;
import ru.sivak.domain.entities.Car;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

public interface ICarService {
     CarDto save(Car car);

     void delete(Id id);

     List<CarDto> findAll();

     List<CarDto> query(CarQuery query);

     CarDto get(Id id);
}
