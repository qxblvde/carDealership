package ru.sivak.application.query;

import lombok.Builder;
import lombok.Getter;
import ru.sivak.domain.valueObjects.ComponentName;
import ru.sivak.domain.valueObjects.ModelName;
import ru.sivak.domain.valueObjects.Money;

@Getter
@Builder
public class WheelQuery {
    private final Money minPrice;
    private final Money maxPrice;
    private final ModelName modelName;
    private final ComponentName componentName;
}
