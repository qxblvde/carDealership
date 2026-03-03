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

    public void addComponent(@NonNull ComponentType componentType, @NonNull Component component) {
        if (!component.isSuitableWith(car.getModelName())) {
            throw new IncompatibleComponentException(
                    componentType,
                    component.getName(),
                    car.getModelName()
            );
        }
        components.put(componentType, component);
    }

    public void validate() {
        for (ComponentType componentType : car.getRequiredComponentTypes()) {
            if (!components.containsKey(componentType)) {
                throw DomainValidationException.missingNode(componentType.getName());
            }
        }
    }

    public Money calculatePrice() {
        Money total = car.getPrice();
        for (Component component : components.values()) {
            total = total.add(component.getPrice());
        }
        return total;
    }
}
