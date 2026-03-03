package ru.sivak.domain.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import ru.sivak.domain.valueObjects.*;
import ru.sivak.domain.valueObjects.Color;

import java.util.Collections;
import java.util.List;

@Getter
@Builder
public final class Car {
    @NonNull
    private final Id id;
    @NonNull
    private final BodyType bodyType;
    @NonNull
    private final BrandName brandName;
    @NonNull
    private final Color color;
    @NonNull
    private final DriveType driveType;
    @NonNull
    private final EnginePower enginePower;
    @NonNull
    private final EngineVolume engineVolume;
    @NonNull
    private final FuelType fuelType;
    @NonNull
    private final ModelName modelName;
    @NonNull
    private final Money price;
    @NonNull
    private final TransmissionType transmissionType;
    @Builder.Default
    private final List<Component> components = Collections.emptyList();

    public List<ComponentType> getRequiredComponentTypes() {
        return List.of(
                ComponentType.of("Wheels"),
                ComponentType.of("Transmisson"),
                ComponentType.of("Steering"),
                ComponentType.of("Interior")
        );
    }
}
