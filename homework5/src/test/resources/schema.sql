
CREATE TABLE authors
(
    id         SERIAL PRIMARY KEY NOT NULL,
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
);


CREATE TABLE books
(
    id        SERIAL PRIMARY KEY NOT NULL,
    book_name VARCHAR(255),
    author_id SERIAL REFERENCES authors,
    genre_id  BIGSERIAL REFERENCES genres
);