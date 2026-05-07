package ru.sivak.application.mappers;

import org.mapstruct.Mapper;
import ru.sivak.application.dto.EngineDto;
import ru.sivak.domain.entities.Engine;

@Mapper(componentModel = "spring")
public interface EngineMapper {

    EngineDto map(Engine engine);
}
