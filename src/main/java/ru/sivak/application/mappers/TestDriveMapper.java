package ru.sivak.application.mappers;

import ru.sivak.application.dto.TestDriveRequestDto;
import ru.sivak.domain.entities.TestDriveRequest;

public final class TestDriveMapper {
    private TestDriveMapper() {}

    public static TestDriveRequestDto toDto(TestDriveRequest request) {
        return new TestDriveRequestDto(
                request.getId(),
                request.getClientId(),
                request.getCarId(),
                request.getScheduledTime()
        );
    }
}
