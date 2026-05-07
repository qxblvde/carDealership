CREATE TABLE app_users (
    id UUID PRIMARY KEY,
    user_role VARCHAR(64) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE car_models (
    id UUID PRIMARY KEY,
    model_name VARCHAR(120) NOT NULL UNIQUE,
    price NUMERIC(12, 0) NOT NULL CHECK (price >= 0),
    component_name VARCHAR(120) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE bodies (
    id UUID PRIMARY KEY,
    price NUMERIC(12, 0) NOT NULL CHECK (price >= 0),
    component_name VARCHAR(120) NOT NULL,
    body_type VARCHAR(120) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE brands (
    id UUID PRIMARY KEY,
    price NUMERIC(12, 0) NOT NULL CHECK (price >= 0),
    component_name VARCHAR(120) NOT NULL,
    brand_name VARCHAR(120) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE colors (
    id UUID PRIMARY KEY,
    price NUMERIC(12, 0) NOT NULL CHECK (price >= 0),
    component_name VARCHAR(120) NOT NULL,
    color_value VARCHAR(120) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE drives (
    id UUID PRIMARY KEY,
    price NUMERIC(12, 0) NOT NULL CHECK (price >= 0),
    component_name VARCHAR(120) NOT NULL,
    drive_type VARCHAR(120) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE engines (
    id UUID PRIMARY KEY,
    price NUMERIC(12, 0) NOT NULL CHECK (price >= 0),
    component_name VARCHAR(120) NOT NULL,
    engine_power INTEGER NOT NULL CHECK (engine_power >= 0),
    engine_volume INTEGER NOT NULL CHECK (engine_volume >= 0),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE fuels (
    id UUID PRIMARY KEY,
    price NUMERIC(12, 0) NOT NULL CHECK (price >= 0),
    component_name VARCHAR(120) NOT NULL,
    fuel_type VARCHAR(120) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE transmissions (
    id UUID PRIMARY KEY,
    price NUMERIC(12, 0) NOT NULL CHECK (price >= 0),
    component_name VARCHAR(120) NOT NULL,
    transmission_type VARCHAR(120) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE steerings (
    id UUID PRIMARY KEY,
    price NUMERIC(12, 0) NOT NULL CHECK (price >= 0),
    component_name VARCHAR(120) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE wheels (
    id UUID PRIMARY KEY,
    price NUMERIC(12, 0) NOT NULL CHECK (price >= 0),
    component_name VARCHAR(120) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE interiors (
    id UUID PRIMARY KEY,
    price NUMERIC(12, 0) NOT NULL CHECK (price >= 0),
    component_name VARCHAR(120) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE body_suitable_models (
    body_id UUID NOT NULL REFERENCES bodies (id),
    model_id UUID NOT NULL REFERENCES car_models (id),
    PRIMARY KEY (body_id, model_id)
);

CREATE TABLE brand_suitable_models (
    brand_id UUID NOT NULL REFERENCES brands (id),
    model_id UUID NOT NULL REFERENCES car_models (id),
    PRIMARY KEY (brand_id, model_id)
);

CREATE TABLE color_suitable_models (
    color_id UUID NOT NULL REFERENCES colors (id),
    model_id UUID NOT NULL REFERENCES car_models (id),
    PRIMARY KEY (color_id, model_id)
);

CREATE TABLE drive_suitable_models (
    drive_id UUID NOT NULL REFERENCES drives (id),
    model_id UUID NOT NULL REFERENCES car_models (id),
    PRIMARY KEY (drive_id, model_id)
);

CREATE TABLE engine_suitable_models (
    engine_id UUID NOT NULL REFERENCES engines (id),
    model_id UUID NOT NULL REFERENCES car_models (id),
    PRIMARY KEY (engine_id, model_id)
);

CREATE TABLE fuel_suitable_models (
    fuel_id UUID NOT NULL REFERENCES fuels (id),
    model_id UUID NOT NULL REFERENCES car_models (id),
    PRIMARY KEY (fuel_id, model_id)
);

CREATE TABLE transmission_suitable_models (
    transmission_id UUID NOT NULL REFERENCES transmissions (id),
    model_id UUID NOT NULL REFERENCES car_models (id),
    PRIMARY KEY (transmission_id, model_id)
);

CREATE TABLE steering_suitable_models (
    steering_id UUID NOT NULL REFERENCES steerings (id),
    model_id UUID NOT NULL REFERENCES car_models (id),
    PRIMARY KEY (steering_id, model_id)
);

CREATE TABLE wheel_suitable_models (
    wheel_id UUID NOT NULL REFERENCES wheels (id),
    model_id UUID NOT NULL REFERENCES car_models (id),
    PRIMARY KEY (wheel_id, model_id)
);

CREATE TABLE interior_suitable_models (
    interior_id UUID NOT NULL REFERENCES interiors (id),
    model_id UUID NOT NULL REFERENCES car_models (id),
    PRIMARY KEY (interior_id, model_id)
);

CREATE TABLE cars (
    id UUID PRIMARY KEY,
    body_id UUID NOT NULL REFERENCES bodies (id),
    brand_id UUID NOT NULL REFERENCES brands (id),
    color_id UUID NOT NULL REFERENCES colors (id),
    drive_id UUID NOT NULL REFERENCES drives (id),
    engine_id UUID NOT NULL REFERENCES engines (id),
    fuel_id UUID NOT NULL REFERENCES fuels (id),
    model_id UUID NOT NULL REFERENCES car_models (id),
    transmission_id UUID NOT NULL REFERENCES transmissions (id),
    steering_id UUID NOT NULL REFERENCES steerings (id),
    wheel_id UUID NOT NULL REFERENCES wheels (id),
    interior_id UUID NOT NULL REFERENCES interiors (id),
    price NUMERIC(12, 0) NOT NULL CHECK (price >= 0),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_car_models_name ON car_models (model_name);
CREATE INDEX idx_cars_price ON cars (price);
CREATE INDEX idx_cars_brand_model ON cars (brand_id, model_id);
CREATE INDEX idx_body_suitable_models_model ON body_suitable_models (model_id);
CREATE INDEX idx_brand_suitable_models_model ON brand_suitable_models (model_id);
CREATE INDEX idx_color_suitable_models_model ON color_suitable_models (model_id);
CREATE INDEX idx_drive_suitable_models_model ON drive_suitable_models (model_id);
CREATE INDEX idx_engine_suitable_models_model ON engine_suitable_models (model_id);
CREATE INDEX idx_fuel_suitable_models_model ON fuel_suitable_models (model_id);
CREATE INDEX idx_transmission_suitable_models_model ON transmission_suitable_models (model_id);
CREATE INDEX idx_steering_suitable_models_model ON steering_suitable_models (model_id);
CREATE INDEX idx_wheel_suitable_models_model ON wheel_suitable_models (model_id);
CREATE INDEX idx_interior_suitable_models_model ON interior_suitable_models (model_id);
