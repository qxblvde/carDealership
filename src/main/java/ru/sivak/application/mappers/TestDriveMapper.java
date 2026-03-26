package ru.sivak.application.mappers;

import org.mapstruct.Mapper;
import ru.sivak.application.dto.TestDriveRequestDto;
import ru.sivak.domain.entities.TestDriveRequest;

@Mapper(componentModel = "spring")
public interface TestDriveMapper {

    TestDriveRequestDto map(TestDriveRequest request);
}
