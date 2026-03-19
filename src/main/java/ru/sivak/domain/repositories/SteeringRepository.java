package ru.sivak.domain.repositories;

import ru.sivak.application.query.SteeringQuery;
import ru.sivak.domain.entities.Steering;

import java.util.List;

public interface SteeringRepository extends Repository<Steering> {

    List<Steering> query(SteeringQuery query);

}