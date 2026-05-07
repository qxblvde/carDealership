package ru.sivak.domain.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import ru.sivak.domain.valueObjects.*;

import java.util.Set;

@SuperBuilder
@Getter
public final class Body extends Component {
    private final BodyType bodyType;
    public Body(@NonNull Id id, @NonNull Money price, @NonNull ComponentName name, @NonNull Set<ModelName> suitableModels, @NonNull BodyType bodyType) {
        super(id, price, name, ComponentType.of("Body"), suitableModels);
        this.bodyType = bodyType;
    }
}
