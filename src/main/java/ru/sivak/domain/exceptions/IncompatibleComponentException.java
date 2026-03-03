package ru.sivak.domain.exceptions;

import ru.sivak.domain.valueObjects.ComponentName;
import ru.sivak.domain.valueObjects.ComponentType;
import ru.sivak.domain.valueObjects.ModelName;

public class IncompatibleComponentException extends DomainException {
    public IncompatibleComponentException(
            ComponentType componentType,
            ComponentName componentName,
            ModelName modelName
            ) {
        super(componentType + " " + componentName + " is not compatible with " + modelName);
    }
}
