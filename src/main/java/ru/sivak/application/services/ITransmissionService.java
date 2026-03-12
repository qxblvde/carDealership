package ru.sivak.application.services;

import ru.sivak.application.dto.TransmissonDto;
import ru.sivak.application.query.TransmissionQuery;
import ru.sivak.domain.entities.Transmission;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

public interface ITransmissionService {
    TransmissonDto save(Transmission transmission);

    void delete(Id id);

    List<TransmissonDto> findAll();

    List<TransmissonDto> query(TransmissionQuery query);

    TransmissonDto get(Id id);
}
