package ru.sivak.application.mappers;

import ru.sivak.application.dto.InteriorDto;
import ru.sivak.domain.entities.Interior;

public class InteriorMapper {

    private InteriorMapper() {}

    public static InteriorDto toDto(Interior interior) {
        return new InteriorDto(
                interior.getPrice(),
                interior.getComponentName(),
                interior.getSuitableModels()
        );
    }
}