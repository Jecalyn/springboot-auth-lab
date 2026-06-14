CREATE TABLE booking_schema.bookings (
    id BIGSERIAL PRIMARY KEY,

    booking_code VARCHAR(50) UNIQUE NOT NULL,

    username VARCHAR(100) NOT NULL,

    event_id BIGINT NOT NULL,

    quantity INTEGER NOT NULL,

    booking_status VARCHAR(30) NOT NULL,

    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);