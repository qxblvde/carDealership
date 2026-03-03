package ru.sivak.application.services;

import ru.sivak.application.dto.TestDriveRequestDto;
import ru.sivak.application.query.TestDriveRequestQuery;
import ru.sivak.domain.valueObjects.Id;

import java.time.LocalDate;
import java.util.List;

public interface ITestDriveRequestService {
    TestDriveRequestDto create(Id clientId, Id carId, LocalDate scheduledTime);
    List<TestDriveRequestDto> query(TestDriveRequestQuery query);
    void delete(Id id);
    TestDriveRequestDto get(Id id);
}
