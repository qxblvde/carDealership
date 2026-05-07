package ru.sivak.application.mappers;

import org.mapstruct.Mapper;
import ru.sivak.application.dto.InteriorDto;
import ru.sivak.domain.entities.Interior;

@Mapper(componentModel = "spring")
public interface InteriorMapper {

    InteriorDto map(Interior interior);
}
