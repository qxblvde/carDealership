package ru.sivak.application.mappers;

import ru.sivak.application.dto.BodyDto;
import ru.sivak.domain.entities.Body;


public class BodyMapper {
    private BodyMapper() {}

    public static BodyDto toDto(Body body) {
        return new BodyDto(
                body.getBodyType(),
                body.getPrice(),
                body.getComponentName(),
                body.getSuitableModels()
        );
    }
}