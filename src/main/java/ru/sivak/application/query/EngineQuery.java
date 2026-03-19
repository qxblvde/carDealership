package ru.sivak.application.query;

import lombok.Builder;
import lombok.Getter;
import ru.sivak.domain.valueObjects.*;

@Getter
@Builder
public class EngineQuery {
    private final EnginePower power;
    private final EngineVolume volume;
    private final Money minPrice;
    private final Money maxPrice;
    private final ModelName modelName;
    private final ComponentName componentName;
}
