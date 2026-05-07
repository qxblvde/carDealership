package ru.sivak.application.mappers;

import org.mapstruct.Mapper;
import ru.sivak.application.dto.ColorDto;
import ru.sivak.domain.entities.Color;

@Mapper(componentModel = "spring")
public interface ColorMapper {

    ColorDto map(Color color);
}
