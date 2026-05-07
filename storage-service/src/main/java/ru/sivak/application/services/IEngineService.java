package ru.sivak.application.services;

import ru.sivak.application.dto.EngineDto;
import ru.sivak.application.query.EngineQuery;
import ru.sivak.domain.entities.Engine;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

public interface IEngineService {
    EngineDto create(Engine engine);

    EngineDto update(Engine engine);

    void delete(Id id);

    List<EngineDto> findAll();

    List<EngineDto> query(EngineQuery query);

    EngineDto get(Id id);
}
