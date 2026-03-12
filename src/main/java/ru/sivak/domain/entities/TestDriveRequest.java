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
    private Id clientId;
    @NonNull
    private Id carId;
    @NonNull
    private LocalDate scheduledTime;

    public void updateClient(@NonNull Id newClientId) {
        this.clientId = newClientId;
    }

    public void updateCar(@NonNull Id newCarId) {
        this.carId = newCarId;
    }

    public void updateScheduledTime(@NonNull LocalDate newTime) {
        this.scheduledTime = newTime;
    }
}
