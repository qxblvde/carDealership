package ru.sivak.domain.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import ru.sivak.domain.valueObjects.Id;

import java.time.LocalDate;

@Builder
@Getter
public final class TestDriveRequest {
    @NonNull
    private final Id id;
    @NonNull
    private final Id clientId;
    @NonNull
    private final Id carId;
    @NonNull
    private final LocalDate scheduledTime;
}
