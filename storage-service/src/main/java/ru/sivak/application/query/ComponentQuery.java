package ru.sivak.application.query;

import lombok.Builder;
import lombok.Getter;
import ru.sivak.domain.valueObjects.ComponentName;
import ru.sivak.domain.valueObjects.ComponentType;
import ru.sivak.domain.valueObjects.ModelName;
import ru.sivak.domain.valueObjects.Money;

@Getter
@Builder
public class ComponentQuery {
    private final ComponentType type;
    private final ComponentName name;
    private final ModelName modelName;
    private final Money minPrice;
    private final Money maxPrice;
}
