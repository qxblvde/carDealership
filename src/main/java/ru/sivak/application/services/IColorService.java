package ru.sivak.application.services;

import ru.sivak.application.dto.ColorDto;
import ru.sivak.application.query.ColorQuery;
import ru.sivak.domain.entities.Color;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

public interface IColorService {
    ColorDto create(Color color);

    ColorDto update(Color color);

    void delete(Id id);

    List<ColorDto> findAll();

    List<ColorDto> query(ColorQuery query);

    ColorDto get(Id id);
}
