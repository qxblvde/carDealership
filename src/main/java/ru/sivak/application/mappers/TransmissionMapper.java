package ru.sivak.application.mappers;

import ru.sivak.application.dto.TransmissonDto;
import ru.sivak.domain.entities.Transmission;

public class TransmissionMapper {
    private TransmissionMapper() {}

    public static TransmissonDto toDto(Transmission transmission) {
        return new TransmissonDto(
                transmission.getTransmissionType(),
                transmission.getPrice(),
                transmission.getComponentName(),
                transmission.getSuitableModels()
        );
    }
}
