package ru.otus.spring.homework.dao.book;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.domain.book.Book;
import ru.otus.spring.homework.exception.CreationException;
import ru.otus.spring.homework.exception.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public List<Book> getAll() {
        return jdbcOperations.query("""
                        select books.id as book_id,
                           book_name as book_name,
                           a.id as author_id,
                           first_name as author_first_name,
                           last_name as author_last_name,
                           g.id as genre_id,
                           genre_name as genre_name
                        from books
                            left join authors a on a.id = books.author_id
                            left join genres g on g.id = books.genre_id""",
                new BookMapper());
    }

    @Override
    public Book save(final Book book) {
        if (book.id() == null) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(final Long id) {
        jdbcOperations.update("delete from books where id = :id", Map.of("id", id));
    }

    @Override
    public Optional<Book> getById(final Long id) {
        try {
            return Optional.ofNullable(
                    jdbcOperations.queryForObject("""
                                    select books.id as book_id,
                                       book_name as book_name,
                                       a.id as author_id,
                                       first_name as author_first_name,
                                       last_name as author_last_name,
                                       g.id as genre_id,
                                       genre_name as genre_name
                                    from books
                                        left join authors a on a.id = books.author_id
                                        left join genres g on g.id = BOOKS.genre_id
                                    where books.id = :id""",
                            Map.of("id", id), new BookMapper()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    private Book insert(final Book book) {
        var keyHolder = new GeneratedKeyHolder();

        var source = new MapSqlParameterSource()
                .addValue("name", book.name())
                .addValue("authorId", book.author().id())
                .addValue("genreId", book.genre().id());

        int insertRowCount = jdbcOperations.update(
                "insert into books (book_name, author_id, genre_id) values (:name, :authorId, :genreId)",
                source,
                keyHolder);

        if (insertRowCount != 1) {
            throw new CreationException("Error insert book");
        }

        return book.toBuilder()
                .id(getIdFromKeyHolder(keyHolder))
                .build();
    }

    private Book update(final Book book) {
        Map<String, Object> values = new HashMap<>();

        values.put("id", book.id());
        values.put("name", book.name());
        values.put("authorId", book.author().id());
        values.put("genreId", book.genre().id());

        int updatedRow = jdbcOperations.update(
                "update books set book_name = :name, author_id = :authorId, genre_id = :genreId where id = :id",
                values
        );

        if (updatedRow != 1) {
            throw new NotFoundException("Book", book.id());
        }
        return book;
    }

    private long getIdFromKeyHolder(GeneratedKeyHolder keyHolder) {
        Map<String, Object> keys = keyHolder.getKeys();

        if (keys == null || keys.isEmpty()) {
            return 0;
        }

        return (Long) keys.get("id");
    }
}
