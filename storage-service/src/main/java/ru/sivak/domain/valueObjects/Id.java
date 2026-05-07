package ru.sivak.domain.valueObjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;
@EqualsAndHashCode
@Getter
public final class Id {

    private final UUID id;

    private Id(@NonNull UUID id) {
        this.id = id;
    }

    public static Id of(@NonNull UUID id) {
        return new Id(id);
    }

    public static Id newId() {
        return new Id(UUID.randomUUID());
    }
}
