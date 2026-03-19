package ru.sivak.domain.valueObjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@EqualsAndHashCode
@Getter
public final class ColorValue {
    private final String color;

    private ColorValue(@NonNull String color) {
        this.color = color;
    }

    public static ColorValue of(@NonNull String color) {
        return new ColorValue(color);
    }
}
