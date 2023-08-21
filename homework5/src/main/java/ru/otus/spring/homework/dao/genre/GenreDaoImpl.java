package ru.otus.spring.homework.dao.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.domain.genre.Genre;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Optional<Genre> create(String genreName) {
        var keyHolder = new GeneratedKeyHolder();
        var source = new MapSqlParameterSource()
                .addValue("name", genreName);

        jdbcOperations.update(
                "insert into genres (genre_name) values (:name)",
                source, keyHolder);

        return buildGenreFromKeyHolder(keyHolder);
    }

    private Optional<Genre> buildGenreFromKeyHolder(GeneratedKeyHolder keyHolder) {
        Map<String, Object> keys = keyHolder.getKeys();

        if (keys == null || keys.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(Genre.builder()
                .id((Long) keys.get("id"))
                .genreName((String) keys.get("genre_name"))
                .build());
    }

    @Override
    public List<Genre> getAll() {
        return jdbcOperations.query(
                "select id, genre_name from genres",
                new GenreMapper());
    }

    @Override
    public Optional<Genre> update(long id, String genreName) {

        jdbcOperations.update(
                "update genres set genre_name = :name where id = :id",
                Map.of("id", id, "name", genreName));

        return Optional.of(Genre.builder().id(id).genreName(genreName).build());
    }

    @Override
    public Optional<Genre> getByName(String genreName) {
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject(
                    "select id, genre_name from genres where genre_name = :name",
                    Map.of("name", genreName),
                    new GenreMapper()));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Genre> get(long id) {
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject(
                    "select id, genre_name from genres where id = :id",
                    Map.of("id", id),
                    new GenreMapper()));

        } catch (IncorrectResultSizeDataAccessException | IncorrectResultSetColumnCountException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(long id) {
        int deletedRow = jdbcOperations.update("delete from genres where id = :id",
                Map.of("id", id));

        return deletedRow != 0;
    }
}
