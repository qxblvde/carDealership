package ru.sivak.application.services;

import ru.sivak.application.dto.AvailableCarDto;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

public interface IAvailableCarService {
    List<AvailableCarDto> findAll();

    AvailableCarDto get(Id id);
}
