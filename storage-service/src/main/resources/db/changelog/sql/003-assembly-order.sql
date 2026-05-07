CREATE TABLE assembly_orders (
    id UUID PRIMARY KEY,
    source_order_id UUID NOT NULL,
    source_order_type VARCHAR(64) NOT NULL,
    car_id UUID,
    warehouse_employee_id UUID,
    status VARCHAR(64) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    removed BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT assembly_order_status_check CHECK (
        status IN ('CREATED', 'ASSEMBLED', 'FAIL')
    )
);

CREATE TABLE assembly_order_components (
    assembly_order_id UUID NOT NULL REFERENCES assembly_orders (id),
    component_id UUID NOT NULL,
    PRIMARY KEY (assembly_order_id, component_id)
);

CREATE INDEX idx_assembly_orders_source_order_id ON assembly_orders (source_order_id);
CREATE INDEX idx_assembly_orders_status ON assembly_orders (status);
CREATE INDEX idx_assembly_order_components_order ON assembly_order_components (assembly_order_id);
