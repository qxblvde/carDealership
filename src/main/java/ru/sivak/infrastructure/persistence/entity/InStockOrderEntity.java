package ru.sivak.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "in_stock_orders")
@NoArgsConstructor
public class InStockOrderEntity extends AbstractOrderEntity {
}
