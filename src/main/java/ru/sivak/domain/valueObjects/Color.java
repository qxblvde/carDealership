package ru.sivak.domain.valueObjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@EqualsAndHashCode
@Getter
public final class Color {
    private final String color;

    private Color(@NonNull String color) {
        this.color = color;
    }

    public static Color of(@NonNull String color) {
        return new Color(color);
    }
}
