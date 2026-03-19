package ru.sivak.domain.repositories;

import ru.sivak.application.query.FuelQuery;
import ru.sivak.domain.entities.Fuel;

import java.util.List;

public interface FuelRepository extends Repository<Fuel> {
    List<Fuel> query(FuelQuery query);

}
