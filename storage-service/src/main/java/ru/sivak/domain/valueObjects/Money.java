package ru.sivak.domain.valueObjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
@EqualsAndHashCode
@Getter
public final class Money {
    private final BigDecimal amount;

    private Money(@NonNull BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.amount = amount;
    }

    public static Money of(@NonNull BigDecimal amount) {
        return new Money(amount);
    }

    public Money add(@NonNull Money money) {
        return new Money(amount.add(money.amount));
    }
}
