CREATE TABLE authors
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
);

CREATE TABLE genres
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    genre_name VARCHAR(255) unique
);

CREATE TABLE books
(
    id        BIGSERIAL PRIMARY KEY NOT NULL,
    book_name VARCHAR(255),
    author_id BIGSERIAL REFERENCES authors,
    genre_id  BIGSERIAL REFERENCES genres
);