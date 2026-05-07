package ru.sivak.application.query;

import lombok.Builder;
import lombok.Getter;
import ru.sivak.domain.valueObjects.Id;

import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
public class TestDriveRequestQuery {
    private final Id clientId;
    private final Id carId;
    private final LocalDate fromDate;
    private final LocalDate toDate;
}
