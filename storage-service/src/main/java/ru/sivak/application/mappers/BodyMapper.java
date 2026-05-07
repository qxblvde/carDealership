package ru.sivak.application.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sivak.application.dto.BodyDto;
import ru.sivak.domain.entities.Body;

@Mapper(componentModel = "spring")
public interface BodyMapper {

    @Mapping(target = "type", source = "bodyType")
    @Mapping(target = "modelName", source = "componentName")
    BodyDto map(Body body);
}
