package ru.otus.spring.homework.dao.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.domain.genre.Genre;
import ru.otus.spring.homework.exception.CreationException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Repository
@RequiredArgsConstructor
public class JdbcGenreRepository implements GenreRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Optional<Genre> create(Genre genre) {
        var keyHolder = new GeneratedKeyHolder();

        var source = new MapSqlParameterSource()
                .addValue("name", genre.genreName());

        jdbcOperations.update(
                "insert into genres (genre_name) values (:name)",
                source, keyHolder);

        return Optional.of(keyHolder)
                .map(GeneratedKeyHolder::getKeys)
                .filter(Predicate.not(Map::isEmpty))
                .map(map -> (Long) map.get("id"))
                .map(id -> genre
                        .toBuilder()
                        .id(id)
                        .build());
    }

    @Override
    public List<Genre> getAll() {
        return jdbcOperations.query(
                "select id, genre_name from genres",
                new GenreMapper());
    }

    @Override
    public void update(Genre genre) {
        try {
            jdbcOperations.update(
                    "update genres set genre_name = :name where id = :id",
                    Map.of("id", genre.id(), "name", genre.genreName()));
        } catch (DataAccessException e) {
            throw new CreationException("Error update genre");
        }
    }

    @Override
    public Optional<Genre> getByName(Genre genre) {
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject(
                    "select id, genre_name from genres where genre_name = :name",
                    Map.of("name", genre.genreName()),
                    new GenreMapper()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Genre> getById(long id) {
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject(
                    "select id, genre_name from genres where id = :id",
                    Map.of("id", id),
                    new GenreMapper()));

        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(long id) {
        jdbcOperations.update("delete from genres where id = :id",
                Map.of("id", id));
    }
}
