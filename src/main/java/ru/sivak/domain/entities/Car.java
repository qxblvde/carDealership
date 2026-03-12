    package ru.sivak.domain.entities;

    import lombok.Builder;
    import lombok.Getter;
    import lombok.NonNull;
    import ru.sivak.domain.valueObjects.*;
    import ru.sivak.domain.valueObjects.ColorValue;

    import java.util.Collections;
    import java.util.List;

    @Getter
    @Builder
    public final class Car {
        @NonNull
        private final Id id;
        @NonNull
        private final Body bodyType;
        @NonNull
        private final Brand brandName;
        @NonNull
        private final Color color;
        @NonNull
        private final Drive driveType;
        @NonNull
        private final Engine engine;
        @NonNull
        private final Fuel fuel;
        @NonNull
        private final Model model;
        @NonNull
        private final Money price;
        @NonNull
        private final Transmission transmission;
        @NonNull
        private final Steering steering;
        @NonNull
        private final Wheel wheel;
        @NonNull
        private final Interior interior;

        public List<ComponentType> getRequiredComponentTypes() {
            return List.of(
                    ComponentType.of("Wheels"),
                    ComponentType.of("Transmission"),
                    ComponentType.of("Steering"),
                    ComponentType.of("Interior")
            );
        }
    }
