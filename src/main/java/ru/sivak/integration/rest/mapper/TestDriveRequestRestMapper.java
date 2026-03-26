package ru.sivak.integration.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sivak.application.query.TestDriveRequestQuery;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.integration.rest.dto.CreateTestDriveRequest;
import ru.sivak.integration.rest.dto.UpdateTestDriveRequest;

import java.time.LocalDate;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TestDriveRequestRestMapper {

    @Mapping(target = "clientId", source = "request.clientId")
    @Mapping(target = "carId", source = "request.carId")
    @Mapping(target = "scheduledTime", source = "request.scheduledTime")
    CreateCommand toCreateCommand(CreateTestDriveRequest request);

    @Mapping(target = "requestId", source = "requestId")
    @Mapping(target = "clientId", source = "request.clientId")
    @Mapping(target = "carId", source = "request.carId")
    @Mapping(target = "scheduledTime", source = "request.scheduledTime")
    UpdateCommand toUpdateCommand(UUID requestId, UpdateTestDriveRequest request);

    @Mapping(target = "clientId", source = "clientId")
    @Mapping(target = "carId", source = "carId")
    @Mapping(target = "fromDate", source = "fromDate")
    @Mapping(target = "toDate", source = "toDate")
    TestDriveRequestQuery toQuery(UUID clientId, UUID carId, LocalDate fromDate, LocalDate toDate);

    default Id toId(UUID value) {
        return value == null ? null : Id.of(value);
    }

    record CreateCommand(Id clientId, Id carId, LocalDate scheduledTime) {
    }

    record UpdateCommand(Id requestId, Id clientId, Id carId, LocalDate scheduledTime) {
    }
}
