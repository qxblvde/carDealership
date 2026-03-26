package ru.sivak.application.mappers;

import org.mapstruct.Mapper;
import ru.sivak.application.dto.FuelDto;
import ru.sivak.domain.entities.Fuel;

@Mapper(componentModel = "spring")
public interface FuelMapper {

    FuelDto map(Fuel fuel);
}
