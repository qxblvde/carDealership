package ru.sivak.domain.valueObjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
@EqualsAndHashCode
@Getter
public final class EnginePower {
    private final int power;

    private EnginePower(int power) {
        if (power < 0) {
            throw new IllegalArgumentException("Power cannot be negative");
        }
        this.power = power;
    }

    public static EnginePower of(int power) {
        return new EnginePower(power);
    }
}
