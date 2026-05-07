package ru.sivak.domain.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import ru.sivak.domain.valueObjects.*;

import java.util.Set;
@SuperBuilder
@Getter
public final class Color extends Component {
    private final ColorValue color;

    public Color(@NonNull Id id, @NonNull Money price, @NonNull ComponentName name, @NonNull Set<ModelName> suitableModels, ColorValue color) {
        super(id, price, name, ComponentType.of("Color"), suitableModels);
        this.color = color;
    }
}
