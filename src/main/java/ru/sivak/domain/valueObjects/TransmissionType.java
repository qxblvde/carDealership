package ru.sivak.domain.valueObjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@EqualsAndHashCode
@Getter
public final class TransmissionType {
    private final String type;

    private TransmissionType(@NonNull String type) {
        this.type = type;
    }

    public static TransmissionType of(@NonNull String name) {
        return new TransmissionType(name);
    }
}
