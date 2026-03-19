package ru.sivak.application.mappers;

import ru.sivak.application.dto.DriveDto;
import ru.sivak.domain.entities.Drive;

public class DriveMapper {
    private DriveMapper() {}

    public static DriveDto toDriveDto(Drive drive) {
        return new DriveDto(
                drive.getDriveType(),
                drive.getPrice(),
                drive.getComponentName(),
                drive.getSuitableModels()
        );
    }
}
