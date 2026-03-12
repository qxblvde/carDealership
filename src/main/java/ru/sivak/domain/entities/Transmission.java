package ru.sivak.domain.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import ru.sivak.domain.valueObjects.*;

import java.util.Set;
@SuperBuilder
@Getter
public final class Transmission extends Component {
    private final TransmissionType transmissionType;
    public Transmission(@NonNull Id id, @NonNull Money price, @NonNull ComponentName name, @NonNull Set<ModelName> suitableModels, @NonNull TransmissionType transmissionType) {

        super(id, price, name, ComponentType.of("Transmission"), suitableModels);
        this.transmissionType = transmissionType;
    }
}
