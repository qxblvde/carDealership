package ru.sivak.application.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sivak.application.dto.TransmissonDto;
import ru.sivak.domain.entities.Transmission;

@Mapper(componentModel = "spring")
public interface TransmissionMapper {
    @Mapping(target = "type", source = "transmissionType")
    TransmissonDto map(Transmission transmission);
}
