
 CREATE TABLE event_schema.events (
    id BIGSERIAL PRIMARY KEY,
    event_code VARCHAR(50) UNIQUE NOT NULL,
    event_name VARCHAR(255) NOT NULL,
    description TEXT,
    venue VARCHAR(255) NOT NULL,
    event_datetime TIMESTAMP NOT NULL,
    max_capacity INTEGER NOT NULL,
    available_slots INTEGER NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);
 