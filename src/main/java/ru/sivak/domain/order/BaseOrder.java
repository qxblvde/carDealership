package ru.sivak.domain.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
    protected Money price;

    protected void setClientId(@NonNull Id clientId) {
        this.clientId = clientId;
    }

    protected void setPrice(@NonNull Money price) {
        this.price = price;
    }
}
