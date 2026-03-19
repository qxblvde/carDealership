package ru.sivak.application.services;

import ru.sivak.application.dto.BodyDto;
import ru.sivak.application.query.BodyQuery;
import ru.sivak.domain.entities.Body;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

public interface IBodyService {
    BodyDto save(Body body);

    void delete(Id id);

    List<BodyDto> findAll();

    List<BodyDto> query(BodyQuery query);

    BodyDto get(Id id);
}
