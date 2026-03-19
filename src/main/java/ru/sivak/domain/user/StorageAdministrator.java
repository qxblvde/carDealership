package ru.sivak.domain.user;

import lombok.Getter;
import lombok.NonNull;
import ru.sivak.domain.valueObjects.Id;

@Getter
public final class StorageAdministrator {
    private final Id id;

    private StorageAdministrator(@NonNull Id id) {
        this.id = id;
    }

    public static StorageAdministrator of(@NonNull Id id) {
        return new StorageAdministrator(id);
    }
}
