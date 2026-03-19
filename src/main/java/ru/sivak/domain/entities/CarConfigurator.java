package ru.sivak.domain.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.sivak.domain.exceptions.DomainValidationException;
import ru.sivak.domain.exceptions.IncompatibleComponentException;
import ru.sivak.domain.valueObjects.ComponentType;
import ru.sivak.domain.valueObjects.Money;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public final class CarConfigurator {

    @NonNull
    private final Car car;

    private final Map<ComponentType, Component> components = new HashMap<>();

    public void addComponent(@NonNull Component component) {

        if (!component.isSuitableWith(car.getModel().getModelName())) {
            throw new IncompatibleComponentException(
                    component.getComponentType(),
                    component.getComponentName(),
                    car.getModel().getModelName()
            );
        }

        components.put(component.getComponentType(), component);
    }

    public void validate() {
        for (ComponentType requiredType : car.getRequiredComponentTypes()) {
            if (!components.containsKey(requiredType)) {
                throw DomainValidationException.missingNode(requiredType.getName());
            }
        }

    }

    public Money calculatePrice() {
        return components.values()
                .stream()
                .map(Component::getPrice)
                .reduce(car.getPrice(), Money::add);

    }
}