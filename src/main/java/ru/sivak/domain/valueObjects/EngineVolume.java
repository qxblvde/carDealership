package ru.sivak.domain.valueObjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
@EqualsAndHashCode
@Getter
public final class EngineVolume {
    private final int volume;

    private EngineVolume(int volume) {
        if (volume < 0) {
            throw new IllegalArgumentException("Volume cannot be negative");
        }
        this.volume = volume;
    }

    public static EngineVolume of(int volume) {
        return new EngineVolume(volume);
    }
}
