CREATE TABLE booking (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    property_id BIGINT NOT NULL,
    guest_name VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE block (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    property_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    reason VARCHAR(255)
);

-- index to optimize search for property_id
CREATE INDEX idx_booking_property ON booking(property_id);
CREATE INDEX idx_block_property ON block(property_id);