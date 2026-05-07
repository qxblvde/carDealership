package ru.sivak.application.mappers;

import org.mapstruct.Mapper;
import ru.sivak.application.dto.ModelDto;
import ru.sivak.domain.entities.Model;

@Mapper(componentModel = "spring")
public interface ModelMapper {

    ModelDto map(Model model);
}
