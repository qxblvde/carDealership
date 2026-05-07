package ru.sivak.domain.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import ru.sivak.domain.valueObjects.*;

import java.util.Set;
@SuperBuilder
@Getter
public final class Fuel extends Component {
    private final FuelType fuelType;
    public Fuel(@NonNull Id id, @NonNull Money price, @NonNull ComponentName name, @NonNull Set<ModelName> suitableModels, @NonNull FuelType fuelType) {
        super(id, price, name, ComponentType.of("Fuel"), suitableModels);
        this.fuelType = fuelType;
    }
}
