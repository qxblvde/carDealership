package ru.sivak.domain.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import ru.sivak.domain.valueObjects.*;

import java.util.Set;
@SuperBuilder
@Getter
public final class Model extends Component {
    private final ModelName modelName;
    public Model(@NonNull Id id, @NonNull Money price, @NonNull ComponentName name, @NonNull Set<ModelName> suitableModels, @NonNull ModelName modelName) {
        super(id, price, name, ComponentType.of("Model"), suitableModels);
        this.modelName = modelName;
    }
}
