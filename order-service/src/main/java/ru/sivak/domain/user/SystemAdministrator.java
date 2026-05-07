package ru.sivak.domain.user;

import lombok.Getter;
import lombok.NonNull;
import ru.sivak.domain.valueObjects.Id;

@Getter
public final class SystemAdministrator {
    private final Id id;

    private SystemAdministrator(@NonNull Id id) {
        this.id = id;
    }

    public static SystemAdministrator of(@NonNull Id id) {
        return new SystemAdministrator(id);
    }
}
