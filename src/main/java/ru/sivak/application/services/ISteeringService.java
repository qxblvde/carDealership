package ru.sivak.application.services;

import ru.sivak.application.dto.SteeringDto;
import ru.sivak.application.query.SteeringQuery;
import ru.sivak.domain.entities.Steering;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

public interface ISteeringService {

    SteeringDto save(Steering steering);

    void delete(Id id);

    List<SteeringDto> findAll();

    List<SteeringDto> query(SteeringQuery query);

    SteeringDto get(Id id);

}