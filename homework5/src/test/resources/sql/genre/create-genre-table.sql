CREATE TABLE genres
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    genre_name VARCHAR(255) unique
);