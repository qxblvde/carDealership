package ru.sivak.domain.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import ru.sivak.domain.valueObjects.*;

import java.util.Set;
@SuperBuilder
@Getter
public final class Engine extends Component {
    private final EnginePower power;
    private final EngineVolume volume;
    public Engine(@NonNull Id id, @NonNull Money price, @NonNull ComponentName name, @NonNull Set<ModelName> suitableModels, @NonNull EnginePower power, @NonNull EngineVolume volume) {
        super(id, price, name, ComponentType.of("Engine"), suitableModels);
        this.power = power;
        this.volume = volume;
    }
}
