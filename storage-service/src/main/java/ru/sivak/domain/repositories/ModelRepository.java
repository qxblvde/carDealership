package ru.sivak.domain.repositories;

import ru.sivak.application.query.ModelQuery;
import ru.sivak.domain.entities.Model;

import java.util.List;

public interface ModelRepository extends Repository<Model> {
    List<Model> query(ModelQuery query);

}
