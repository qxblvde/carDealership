package ru.sivak.application.mappers;

import ru.sivak.application.dto.EngineDto;
import ru.sivak.domain.entities.Engine;

public class EngineMapper {
    private EngineMapper() {}

    public static EngineDto toDto(Engine engine) {
        return new EngineDto(
                engine.getPower(),
                engine.getVolume(),
                engine.getPrice(),
                engine.getComponentName(),
                engine.getSuitableModels()
        );
    }
}
