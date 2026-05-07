CREATE TABLE car_stock (
    id UUID PRIMARY KEY,
    car_id UUID NOT NULL UNIQUE REFERENCES cars (id),
    quantity INTEGER NOT NULL DEFAULT 0 CHECK (quantity >= 0),
    reserved INTEGER NOT NULL DEFAULT 0 CHECK (reserved >= 0),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE component_stock (
    id UUID PRIMARY KEY,
    component_id UUID NOT NULL UNIQUE,
    quantity INTEGER NOT NULL DEFAULT 0 CHECK (quantity >= 0),
    reserved INTEGER NOT NULL DEFAULT 0 CHECK (reserved >= 0),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_car_stock_car_id ON car_stock (car_id);
CREATE INDEX idx_component_stock_component_id ON component_stock (component_id);
