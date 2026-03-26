package ru.sivak.domain.order;

import lombok.*;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.domain.valueObjects.Money;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseOrder {
    @NonNull
    private final Id id;
    @NonNull
    private final Id managerId;
    @NonNull
    private Id clientId;
    @NonNull
    private Id carId;
    @NonNull
    protected Money price;

    protected void setClientId(@NonNull Id clientId) {
        this.clientId = clientId;
    }

    protected void setCarId(@NonNull Id carId) {
        this.carId = carId;
    }

    protected void setPrice(@NonNull Money price) {
        this.price = price;
    }
}
