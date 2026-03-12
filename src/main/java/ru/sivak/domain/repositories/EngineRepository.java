package ru.sivak.domain.repositories;

import ru.sivak.application.query.EngineQuery;
import ru.sivak.domain.entities.Engine;

import java.util.List;

public interface EngineRepository extends Repository<Engine> {
    List<Engine> query(EngineQuery query);

}
