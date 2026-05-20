package ru.sivak.application.services;

import ru.sivak.application.dto.AvailableCarDto;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;
import java.util.Optional;

public interface IAvailableCarService {
    List<AvailableCarDto> findAllAvailable();

    Optional<AvailableCarDto> findAvailable(Id id);
}
