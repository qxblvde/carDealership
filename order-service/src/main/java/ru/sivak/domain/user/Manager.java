package ru.sivak.domain.user;

import lombok.Getter;
import lombok.NonNull;
import ru.sivak.domain.valueObjects.Id;

@Getter
public final class Manager {
    private final Id id;

    private Manager(@NonNull Id id) {
        this.id = id;
    }

    public static Manager of(@NonNull Id id) {
        return new Manager(id);
    }
}
