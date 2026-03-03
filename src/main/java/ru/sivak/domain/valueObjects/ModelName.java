package ru.sivak.domain.valueObjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@EqualsAndHashCode
@Getter
public final class ModelName {
    private final String name;

    private ModelName(@NonNull String name) {
        this.name = name;
    }

    public static ModelName of(@NonNull String name) {
        return new ModelName(name);
    }
}
