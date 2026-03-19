package ru.sivak.domain.valueObjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@EqualsAndHashCode
@Getter
public final class DriveType {
    private final String type;

    private DriveType(@NonNull String type) {
        this.type = type;
    }

    public static DriveType of(@NonNull String type) {
        return new DriveType(type);
    }
}
