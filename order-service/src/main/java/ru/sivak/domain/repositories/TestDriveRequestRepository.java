package ru.sivak.domain.repositories;

import ru.sivak.application.query.TestDriveRequestQuery;
import ru.sivak.domain.entities.TestDriveRequest;

import java.util.List;

public interface TestDriveRequestRepository extends Repository<TestDriveRequest> {
    List<TestDriveRequest> query(TestDriveRequestQuery query);
}
