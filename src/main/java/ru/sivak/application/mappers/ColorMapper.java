package ru.sivak.application.mappers;

import ru.sivak.application.dto.ColorDto;
import ru.sivak.domain.entities.Color;

public class ColorMapper {
    private ColorMapper() {}

    public static ColorDto toDto(Color color) {
        return new ColorDto(
                color.getColor(),
                color.getPrice(),
                color.getComponentName(),
                color.getSuitableModels()
        );
    }
}
