package ru.sivak.domain.user;

import lombok.Getter;
import lombok.NonNull;
import ru.sivak.domain.valueObjects.Id;

@Getter
public final class Client {
    private final Id id;

    private Client(@NonNull Id id) {
        this.id = id;
    }

    public static Client of(@NonNull Id id) {
        return new Client(id);
    }
}
