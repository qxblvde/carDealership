CREATE TABLE app_users (
    id UUID PRIMARY KEY,
    user_role VARCHAR(64) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE custom_orders (
    id UUID PRIMARY KEY,
    manager_id UUID NOT NULL REFERENCES app_users (id),
    client_id UUID NOT NULL REFERENCES app_users (id),
    car_id UUID NOT NULL,
    state VARCHAR(64) NOT NULL,
    price NUMERIC(12, 0) NOT NULL CHECK (price >= 0),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    removed BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT custom_order_state_check CHECK (
        state IN (
            'CREATED',
            'APPROVED',
            'WAITING_PAYMENT',
            'PAID',
            'WAITING_DELIVERY',
            'READY_FOR_PICK_UP',
            'COMPLETED',
            'CANCELED'
        )
    )
);

CREATE TABLE in_stock_orders (
    id UUID PRIMARY KEY,
    manager_id UUID NOT NULL REFERENCES app_users (id),
    client_id UUID NOT NULL REFERENCES app_users (id),
    car_id UUID NOT NULL,
    state VARCHAR(64) NOT NULL,
    price NUMERIC(12, 0) NOT NULL CHECK (price >= 0),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    removed BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT in_stock_order_state_check CHECK (
        state IN (
            'CREATED',
            'APPROVED',
            'WAITING_PAYMENT',
            'PAID',
            'READY_FOR_PICK_UP',
            'COMPLETED',
            'CANCELED'
        )
    )
);

CREATE TABLE test_drive_requests (
    id UUID PRIMARY KEY,
    client_id UUID NOT NULL REFERENCES app_users (id),
    car_id UUID NOT NULL,
    scheduled_time DATE NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_custom_orders_client ON custom_orders (client_id);
CREATE INDEX idx_custom_orders_manager ON custom_orders (manager_id);
CREATE INDEX idx_custom_orders_state ON custom_orders (state);
CREATE INDEX idx_in_stock_orders_client ON in_stock_orders (client_id);
CREATE INDEX idx_in_stock_orders_manager ON in_stock_orders (manager_id);
CREATE INDEX idx_in_stock_orders_state ON in_stock_orders (state);
CREATE INDEX idx_test_drive_requests_client ON test_drive_requests (client_id);
CREATE INDEX idx_test_drive_requests_car ON test_drive_requests (car_id);
CREATE INDEX idx_test_drive_requests_scheduled_time ON test_drive_requests (scheduled_time);
