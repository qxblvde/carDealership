package ru.sivak.domain.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.sivak.domain.valueObjects.*;

import java.util.Set;

@SuperBuilder
@Getter
@RequiredArgsConstructor
public abstract class  Component {
    @NonNull
    private final Id id;
    @NonNull
    private final Money price;
    @NonNull
    private final ComponentName componentName;
    @NonNull
    private final ComponentType componentType;
    @NonNull
    private final Set<ModelName> suitableModels;



    public boolean isSuitableWith(@NonNull ModelName modelName) {
        return suitableModels.contains(modelName);
    }

}
