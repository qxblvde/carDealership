package ru.sivak.domain.valueObjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@EqualsAndHashCode
@Getter
public final class ComponentName {
    private final String name;

    private ComponentName(@NonNull String name) {
        this.name = name;
    }

    public static ComponentName of(@NonNull String name) {

        return new ComponentName(name);
    }
}