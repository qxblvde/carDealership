package ru.sivak.domain.repositories;

import ru.sivak.application.query.ComponentQuery;
import ru.sivak.domain.entities.Component;

import java.util.List;

public interface ComponentRepository extends Repository<Component> {
    List<Component> query(ComponentQuery componentQuery);
}
