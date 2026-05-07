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
@Table(name = "interiors")
@Getter
@Setter
@NoArgsConstructor
public class InteriorEntity extends AbstractComponentEntity {
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "interior_suitable_models",
            joinColumns = @JoinColumn(name = "interior_id"),
            inverseJoinColumns = @JoinColumn(name = "model_id")
    )
    private Set<CarModelEntity> suitableModels = new HashSet<>();

}
