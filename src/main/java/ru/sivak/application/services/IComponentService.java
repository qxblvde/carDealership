package ru.sivak.application.services;

import ru.sivak.application.dto.ComponentDto;
import ru.sivak.application.query.ComponentQuery;
import ru.sivak.domain.entities.Component;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

public interface IComponentService {
    ComponentDto save(Component component);

    void delete(Id id);

    ComponentDto get(Id id);

    List<ComponentDto> query(ComponentQuery query);
}
