package ru.sivak.domain.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import ru.sivak.domain.valueObjects.*;

import java.util.Set;

@SuperBuilder
@Getter
public final class Interior extends Component {
    public Interior(@NonNull Id id, @NonNull Money price, @NonNull ComponentName componentName, @NonNull Set<ModelName> suitableModels) {
        super(id, price, componentName, ComponentType.of("Interior"), suitableModels);
    }
}
