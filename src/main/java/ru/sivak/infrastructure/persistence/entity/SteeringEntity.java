package ru.sivak.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "steerings")
@Getter
@Setter
@NoArgsConstructor
public class SteeringEntity extends AbstractComponentEntity {
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "steering_suitable_models",
            joinColumns = @JoinColumn(name = "steering_id"),
            inverseJoinColumns = @JoinColumn(name = "model_id")
    )
    private Set<CarModelEntity> suitableModels = new HashSet<>();

}
