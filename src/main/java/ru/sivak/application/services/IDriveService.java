package ru.sivak.application.services;

import ru.sivak.application.dto.DriveDto;
import ru.sivak.application.query.DriveQuery;
import ru.sivak.domain.entities.Drive;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

public interface IDriveService {
    DriveDto save(Drive drive);

    void delete(Id id);

    List<DriveDto> findAll();

    List<DriveDto> query(DriveQuery query);

    DriveDto get(Id id);
}
