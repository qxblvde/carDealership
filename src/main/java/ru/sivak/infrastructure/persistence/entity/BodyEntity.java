package ru.sivak.infrastructure.persistence.entity;

import jakarta.persistence.Column;
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
@Table(name = "bodies")
@Getter
@Setter
@NoArgsConstructor
public class BodyEntity extends AbstractComponentEntity {
    @Column(name = "body_type", nullable = false, length = 120)
    private String bodyType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "body_suitable_models",
            joinColumns = @JoinColumn(name = "body_id"),
            inverseJoinColumns = @JoinColumn(name = "model_id")
    )
    private Set<CarModelEntity> suitableModels = new HashSet<>();
}
