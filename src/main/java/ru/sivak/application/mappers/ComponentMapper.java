package ru.sivak.application.mappers;

import ru.sivak.application.dto.ComponentDto;
import ru.sivak.domain.entities.Component;


public final class ComponentMapper {
    private ComponentMapper() {}

    public static ComponentDto toDto(Component component) {
        return new ComponentDto(
                component.getId(),
                component.getName(),
                component.getType(),
                component.getPrice(),
                component.getSuitableModels()
        );
    }
}
