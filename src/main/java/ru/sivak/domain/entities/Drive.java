package ru.sivak.domain.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import ru.sivak.domain.valueObjects.*;

import java.util.Set;
@SuperBuilder
@Getter
public final class Drive extends Component {
    private final DriveType driveType;
    public Drive(@NonNull Id id, @NonNull Money price, @NonNull ComponentName name, @NonNull Set<ModelName> suitableModels, @NonNull DriveType driveType) {
        super(id, price, name, ComponentType.of("Drive"), suitableModels);
        this.driveType = driveType;
    }
}
