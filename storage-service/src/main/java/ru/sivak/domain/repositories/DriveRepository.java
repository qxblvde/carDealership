package ru.sivak.domain.repositories;

import ru.sivak.application.query.DriveQuery;
import ru.sivak.domain.entities.Drive;

import java.util.List;

public interface DriveRepository extends Repository<Drive> {
    List<Drive> query(DriveQuery query);

}
