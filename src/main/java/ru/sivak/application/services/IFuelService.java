package ru.sivak.application.services;

import ru.sivak.application.dto.FuelDto;
import ru.sivak.application.query.FuelQuery;
import ru.sivak.domain.entities.Fuel;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

public interface IFuelService {
    FuelDto save(Fuel fuel);

    void delete(Id id);

    List<FuelDto> findAll();

    List<FuelDto> query(FuelQuery query);

    FuelDto get(Id id);
}
