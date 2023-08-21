package ru.otus.spring.homework.dao.author;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.domain.author.Author;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Optional<Author> create(String firstName, String lastName) {

        var keyHolder = new GeneratedKeyHolder();
        var source = new MapSqlParameterSource()
                .addValue("firstName", firstName)
                .addValue("lastName", lastName);

        jdbcOperations.update("""
                        insert into authors (first_name, last_name) \
                        values (:firstName, :lastName)
                        """,
                source,
                keyHolder
        );

        return buildAuthorFromKeyHolder(keyHolder);
    }

    private Optional<Author> buildAuthorFromKeyHolder(GeneratedKeyHolder keyHolder) {
        Map<String, Object> keys = keyHolder.getKeys();

        if (keys == null || keys.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(Author.builder()
                .id((Long) keys.get("id"))
                .firstName((String) keys.get("first_name"))
                .lastName((String) keys.get("last_name"))
                .build());
    }

    @Override
    public List<Author> getAll() {

        return jdbcOperations.query("select id, first_name, last_name from authors", new AuthorMapper());
    }

    @Override
    public boolean delete(long id) {

        int deletedRow = jdbcOperations.update("delete from authors where id = :id", Map.of("id", id));
        return deletedRow != 0;
    }

    @Override
    public Optional<Author> update(long id, String firstName, String lastName) {

        int update = jdbcOperations.update(
                "update authors set first_name = :firstName, last_name = :lastName where id = :id",
                Map.of("id", id, "firstName", firstName, "lastName", lastName));

        if (update == 0) {
            return Optional.empty();
        } else {
            return Optional.of(Author.builder()
                    .id(id)
                    .firstName(firstName)
                    .lastName(lastName)
                    .build());
        }
    }

    @Override
    public Optional<Author> getById(Long id) {

        return Optional.ofNullable(
                jdbcOperations.queryForObject("select id, first_name, last_name from authors where id = :id",
                        Map.of("id", id),
                        new AuthorMapper())
        );
    }
}
