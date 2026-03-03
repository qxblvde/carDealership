package ru.sivak.domain.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import ru.sivak.domain.valueObjects.*;

import java.util.Set;

@Builder
@Getter
public final class Component {
    @NonNull
    private final Id id;
    @NonNull
    private final Money price;
    @NonNull
    private final ComponentName name;
    @NonNull
    private final ComponentType type;
    @NonNull
    private final Set<ModelName> suitableModels;

    public boolean isSuitableWith(@NonNull ModelName modelName) {
        return suitableModels.contains(modelName);
    }

}
