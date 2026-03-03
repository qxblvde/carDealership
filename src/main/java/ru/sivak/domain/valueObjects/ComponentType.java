package ru.sivak.domain.valueObjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@EqualsAndHashCode
@Getter
public final class ComponentType {
    private final String name;

    private ComponentType(@NonNull String name) {
        this.name = name;
    }

    public static ComponentType of(@NonNull String name) {
        return new ComponentType(name);
    }
}
