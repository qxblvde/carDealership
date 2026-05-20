package ru.sivak.infrastructure.grpc.mapper;

import org.mapstruct.Mapper;
import ru.sivak.application.dto.AvailableCarDto;
import ru.sivak.contract.availablecar.AvailableCarResponse;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AvailableCarGrpcMapper {

    default AvailableCarDto map(AvailableCarResponse response) {
        return new AvailableCarDto(
                UUID.fromString(response.getId()),
                response.getBodyType(),
                response.getBrandName(),
                response.getColor(),
                response.getDriveType(),
                response.getEnginePower(),
                response.getEngineVolume(),
                response.getFuelType(),
                response.getModelName(),
                new BigDecimal(response.getPrice()),
                response.getTransmissionType(),
                response.getAvailableQuantity()
        );
    }
}
