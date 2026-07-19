CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(30) NOT NULL UNIQUE,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       full_name VARCHAR(100) NOT NULL,
                       role VARCHAR(20) NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);