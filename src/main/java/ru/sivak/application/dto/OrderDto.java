package ru.sivak.application.dto;

import ru.sivak.domain.valueObjects.Id;
import ru.sivak.domain.valueObjects.Money;

public record OrderDto(
        Id id,
        Id clientId,
        Id managerId,
        Money total,
        Class<?> stateType
){}
