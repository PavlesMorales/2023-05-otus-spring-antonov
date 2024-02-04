CREATE TABLE IF NOT EXISTS authors
(
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS genres
(
    id   BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255) unique
);

CREATE TABLE IF NOT EXISTS books
(
    id        BIGSERIAL PRIMARY KEY NOT NULL,
    title     VARCHAR(255),
    author_id BIGSERIAL REFERENCES authors (id) on delete cascade,
    genre_id  BIGSERIAL REFERENCES genres (id) on delete cascade
);

CREATE TABLE IF NOT EXISTS comments
(
    id      BIGSERIAL PRIMARY KEY NOT NULL,
    text    VARCHAR(255),
    book_id BIGSERIAL REFERENCES books (id) on delete cascade
);

