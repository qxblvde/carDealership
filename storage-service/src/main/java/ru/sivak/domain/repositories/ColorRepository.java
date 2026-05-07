package ru.sivak.domain.repositories;

import ru.sivak.application.query.ColorQuery;
import ru.sivak.domain.entities.Color;

import java.util.List;

public interface ColorRepository extends Repository<Color> {
    List<Color> query(ColorQuery query);

}
