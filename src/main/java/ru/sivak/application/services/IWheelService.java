package ru.sivak.application.services;

import ru.sivak.application.dto.WheelDto;
import ru.sivak.application.query.WheelQuery;
import ru.sivak.domain.entities.Wheel;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

public interface IWheelService {
    WheelDto create(Wheel wheel);

    WheelDto update(Wheel wheel);

    void delete(Id id);

    List<WheelDto> findAll();

    List<WheelDto> query(WheelQuery query);

    WheelDto get(Id id);
}
