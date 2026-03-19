package ru.sivak.domain.repositories;

import ru.sivak.application.query.TransmissionQuery;
import ru.sivak.domain.entities.Transmission;

import java.util.List;

public interface TransmissonRepository extends Repository<Transmission> {
    List<Transmission> query(TransmissionQuery query);
}
