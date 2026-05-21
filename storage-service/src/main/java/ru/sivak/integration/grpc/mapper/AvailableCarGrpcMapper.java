package ru.sivak.integration.grpc.mapper;

import org.mapstruct.Mapper;
import ru.sivak.application.dto.AvailableCarDto;
import ru.sivak.contract.availablecar.AvailableCarResponse;

@Mapper(componentModel = "spring")
public interface AvailableCarGrpcMapper {

    default AvailableCarResponse map(AvailableCarDto dto) {
        return AvailableCarResponse.newBuilder()
                .setId(dto.id().toString())
                .setBodyType(dto.bodyType())
                .setBrandName(dto.brandName())
                .setColor(dto.color())
                .setDriveType(dto.driveType())
                .setEnginePower(dto.enginePower())
                .setEngineVolume(dto.engineVolume())
                .setFuelType(dto.fuelType())
                .setModelName(dto.modelName())
                .setPrice(dto.price().toPlainString())
                .setTransmissionType(dto.transmissionType())
                .setAvailableQuantity(dto.availableQuantity())
                .build();
    }
}
