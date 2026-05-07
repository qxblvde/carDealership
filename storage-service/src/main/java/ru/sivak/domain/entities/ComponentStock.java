package ru.sivak.domain.entities;

import lombok.Getter;
import lombok.NonNull;
import ru.sivak.domain.valueObjects.Id;

import java.util.UUID;

@Getter
public final class ComponentStock {

    @NonNull
    private final Id id;
    @NonNull
    private final UUID componentId;
    private int quantity;
    private int reserved;

    public ComponentStock(@NonNull Id id, @NonNull UUID componentId, int quantity, int reserved) {
        this.id = id;
        this.componentId = componentId;
        this.quantity = quantity;
        this.reserved = reserved;
    }

    public boolean isAvailable() {
        return (quantity - reserved) > 0;
    }

    public void reserve() {
        if (!isAvailable()) {
            throw new IllegalStateException("No available components in stock");
        }
        reserved++;
    }

    public void release() {
        if (reserved <= 0) {
            throw new IllegalStateException("Nothing to release");
        }
        reserved--;
    }
}
