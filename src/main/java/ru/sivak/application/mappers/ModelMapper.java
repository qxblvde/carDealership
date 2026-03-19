package ru.sivak.application.mappers;

import ru.sivak.application.dto.ModelDto;
import ru.sivak.domain.entities.Model;

public class ModelMapper {
    private ModelMapper() {}

    public static ModelDto toDto(Model model) {
        return new ModelDto(
                model.getModelName(),
                model.getPrice(),
                model.getComponentName()
        );
    }
}
