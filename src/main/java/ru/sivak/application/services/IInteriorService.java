package ru.sivak.application.services;

import ru.sivak.application.dto.InteriorDto;
import ru.sivak.application.query.InteriorQuery;
import ru.sivak.domain.entities.Interior;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

public interface IInteriorService {

    InteriorDto create(Interior interior);

    InteriorDto update(Interior interior);

    void delete(Id id);

    List<InteriorDto> findAll();

    List<InteriorDto> query(InteriorQuery query);

    InteriorDto get(Id id);

}