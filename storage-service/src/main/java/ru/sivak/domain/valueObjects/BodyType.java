package ru.sivak.domain.valueObjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@EqualsAndHashCode
@Getter
public final class BodyType {
    private final String type;

    private BodyType(@NonNull String type) {
        this.type = type;
    }

    public static BodyType of(@NonNull String type) {
        return new BodyType(type);
    }
}
