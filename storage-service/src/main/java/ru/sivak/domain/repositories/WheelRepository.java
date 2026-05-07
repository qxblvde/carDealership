package ru.sivak.domain.repositories;

import ru.sivak.application.query.WheelQuery;
import ru.sivak.domain.entities.Wheel;

import java.util.List;

public interface WheelRepository extends Repository<Wheel> {
    List<Wheel> query(WheelQuery query);

}
