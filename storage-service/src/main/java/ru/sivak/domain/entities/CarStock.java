package ru.sivak.domain.entities;

import lombok.Getter;
import lombok.NonNull;
import ru.sivak.domain.valueObjects.Id;

import java.util.UUID;

@Getter
public final class CarStock {

    @NonNull
    private final Id id;
    @NonNull
    private final UUID carId;
    private int quantity;
    private int reserved;

    public CarStock(@NonNull Id id, @NonNull UUID carId, int quantity, int reserved) {
        this.id = id;
        this.carId = carId;
        this.quantity = quantity;
        this.reserved = reserved;
    }

    public boolean isAvailable() {
        return (quantity - reserved) > 0;
    }

    public void reserve() {
        if (!isAvailable()) {
            throw new IllegalStateException("No available cars in stock");
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
