package ru.sivak.infrastructure.persistence.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "assembly_orders")
@Getter
@Setter
@NoArgsConstructor
public class AssemblyOrderEntity extends BaseJpaEntity {

    @Column(name = "source_order_id", nullable = false)
    private UUID sourceOrderId;

    @Column(name = "source_order_type", nullable = false, length = 64)
    private String sourceOrderType;

    @Column(name = "car_id")
    private UUID carId;

    @Column(name = "warehouse_employee_id")
    private UUID warehouseEmployeeId;

    @Column(name = "status", nullable = false, length = 64)
    private String status;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "assembly_order_components",
            joinColumns = @JoinColumn(name = "assembly_order_id")
    )
    @Column(name = "component_id", nullable = false)
    private Set<UUID> requiredComponentIds = new HashSet<>();
}
