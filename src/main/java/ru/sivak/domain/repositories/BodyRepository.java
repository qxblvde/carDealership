package ru.sivak.domain.repositories;

import ru.sivak.application.query.BodyQuery;
import ru.sivak.domain.entities.Body;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;
import java.util.Optional;

public interface BodyRepository extends Repository<Body> {
    List<Body> query(BodyQuery query);

    Optional<Body> find(Id id);
}
