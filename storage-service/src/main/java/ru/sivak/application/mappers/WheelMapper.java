package ru.sivak.application.mappers;

import org.mapstruct.Mapper;
import ru.sivak.application.dto.WheelDto;
import ru.sivak.domain.entities.Wheel;

@Mapper(componentModel = "spring")
public interface WheelMapper {

    WheelDto map(Wheel wheel);
}
