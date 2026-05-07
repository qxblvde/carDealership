package ru.sivak.domain.valueObjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@EqualsAndHashCode
@Getter
public final class FuelType {
    private final String type;

    private FuelType(@NonNull String type) {
        this.type = type;
    }

    public static FuelType of(@NonNull String type) {
        return new FuelType(type);
    }
}
