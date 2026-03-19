package ru.sivak.domain.valueObjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@EqualsAndHashCode
@Getter
public final class BrandName {
    private final String name;

    private BrandName(@NonNull String name) {
        this.name = name;
    }

    public static BrandName of(@NonNull String name) {
        return new BrandName(name);
    }
}
