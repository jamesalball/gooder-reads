CREATE TABLE IF NOT EXISTS Books (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255),
    author BIGINT
);

CREATE TABLE IF NOT EXISTS Users (
    id BIGINT PRIMARY KEY,
    email VARCHAR(255),
    pass VARCHAR(255),
    display_name VARCHAR(255)
);