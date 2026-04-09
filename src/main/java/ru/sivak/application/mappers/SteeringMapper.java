package ru.sivak.application.mappers;

import org.mapstruct.Mapper;
import ru.sivak.application.dto.SteeringDto;
import ru.sivak.domain.entities.Steering;

@Mapper(componentModel = "spring")
public interface SteeringMapper {

    SteeringDto map(Steering steering);
}
