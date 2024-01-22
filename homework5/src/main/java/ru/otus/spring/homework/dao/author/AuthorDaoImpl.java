package ru.otus.spring.homework.dao.author;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.domain.author.Author;
import ru.otus.spring.homework.exception.CreationException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Optional<Author> create(Author author) {

        var keyHolder = new GeneratedKeyHolder();
        var source = new MapSqlParameterSource()
                .addValue("firstName", author.firstName())
                .addValue("lastName", author.lastName());

        jdbcOperations.update("""
                        insert into authors (first_name, last_name)
                        values (:firstName, :lastName)
                        """,
                source,
                keyHolder
        );

        return Optional.of(keyHolder)
                .map(GeneratedKeyHolder::getKeys)
                .filter(Predicate.not(Map::isEmpty))
                .map(map -> (Long) map.get("id"))
                .map(id -> author
                        .toBuilder()
                        .id(id)
                        .build());

    }

    @Override
    public List<Author> getAll() {
        return jdbcOperations.query("select id, first_name, last_name from authors", new AuthorMapper());
    }

    @Override
    public void delete(Long id) {
        jdbcOperations.update("delete from authors where id = :id", Map.of("id", id));
    }

    @Override
    public void update(Author author) {
        try {
            jdbcOperations.update(
                    "update authors set first_name = :firstName, last_name = :lastName where id = :id",
                    Map.of("id", author.id(), "firstName", author.firstName(), "lastName", author.lastName()));
        } catch (DataAccessException e) {
            throw new CreationException("Error update author");
        }
    }

    @Override
    public Optional<Author> getById(Long id) {
        try {
            return Optional.ofNullable(jdbcOperations.queryForObject(
                    "select id, first_name, last_name from authors where id = :id",
                    Map.of("id", id),
                    new AuthorMapper()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
