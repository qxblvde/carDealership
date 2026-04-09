package ru.sivak.application.mappers;

import org.mapstruct.Mapper;
import ru.sivak.application.dto.DriveDto;
import ru.sivak.domain.entities.Drive;

@Mapper(componentModel = "spring")
public interface DriveMapper {

    DriveDto map(Drive drive);
}
