package ru.sivak.application.dto;

import ru.sivak.domain.valueObjects.Id;

import java.time.LocalDate;

public record TestDriveRequestDto(
        Id id,
        Id clientId,
        Id carId,
        LocalDate scheduledTime
) {}
