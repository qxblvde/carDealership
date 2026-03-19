package ru.sivak.application.services;

import ru.sivak.application.dto.WheelDto;
import ru.sivak.application.query.WheelQuery;
import ru.sivak.domain.entities.Wheel;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

public interface IWheelService {
    WheelDto save(Wheel wheel);

    void delete(Id id);

    List<WheelDto> findAll();

    List<WheelDto> query(WheelQuery query);

    WheelDto get(Id id);
}
