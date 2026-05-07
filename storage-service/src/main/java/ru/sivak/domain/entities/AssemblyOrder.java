package ru.sivak.domain.entities;

import lombok.Getter;
import lombok.NonNull;
import ru.sivak.domain.valueObjects.Id;

import java.util.Set;
import java.util.UUID;

@Getter
public final class AssemblyOrder {

    public enum Status {
        CREATED, ASSEMBLED, FAIL
    }

    @NonNull
    private final Id id;
    @NonNull
    private final UUID sourceOrderId;
    @NonNull
    private final String sourceOrderType;
    private final UUID carId;
    private final UUID warehouseEmployeeId;
    @NonNull
    private final Set<UUID> requiredComponentIds;
    @NonNull
    private Status status;

    public AssemblyOrder(
            @NonNull Id id,
            @NonNull UUID sourceOrderId,
            @NonNull String sourceOrderType,
            UUID carId,
            UUID warehouseEmployeeId,
            @NonNull Set<UUID> requiredComponentIds
    ) {
        this.id = id;
        this.sourceOrderId = sourceOrderId;
        this.sourceOrderType = sourceOrderType;
        this.carId = carId;
        this.warehouseEmployeeId = warehouseEmployeeId;
        this.requiredComponentIds = requiredComponentIds;
        this.status = Status.CREATED;
    }

    public void markAssembled() {
        this.status = Status.ASSEMBLED;
    }

    public void markFail() {
        this.status = Status.FAIL;
    }

    public void updateStatus(@NonNull Status newStatus) {
        this.status = newStatus;
    }
}
