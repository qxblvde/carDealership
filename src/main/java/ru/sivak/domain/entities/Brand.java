package ru.sivak.domain.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import ru.sivak.domain.valueObjects.*;

import java.util.Set;
@SuperBuilder
@Getter
public final class Brand extends Component {
    private final BrandName name;
    public Brand(@NonNull Id id, @NonNull Money price, @NonNull ComponentName name, @NonNull Set<ModelName> suitableModels, @NonNull BrandName brandname) {
        super(id, price, name, ComponentType.of("Brand"), suitableModels);
        this.name = brandname;
    }
}
