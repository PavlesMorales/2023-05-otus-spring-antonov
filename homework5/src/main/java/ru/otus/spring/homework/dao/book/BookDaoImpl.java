package ru.otus.spring.homework.dao.book;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.domain.book.Book;
import ru.otus.spring.homework.exception.CreationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Optional<Book> create(Book book) {
        try {
            long bookId = insertBook(book);

            if (bookId == 0) {
                return Optional.empty();
            }

            return getBook(bookId);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getAll() {
        return jdbcOperations.query("""
                        select books.id as book_id,
                           book_name as book_name,
                           a.id as author_id,
                           first_name as author_first_name,
                           last_name as author_last_name,
                           g.id as genre_id,
                           genre_name as genre_name \
                        from books
                            left join authors a on a.id = books.author_id
                            left join genres g on g.id = books.genre_id""",
                new BookMapper());
    }

    @Override
    public void update(Book book) {
        Map<String, Object> values = new HashMap<>();

        values.put("id", book.id());
        values.put("name", book.name());
        values.put("authorId", book.author().id());
        values.put("genreId", book.genre().id());

        try {
            jdbcOperations.update(
                    "update books set book_name = :name, author_id = :authorId, genre_id = :genreId where id = :id",
                    values
            );
        } catch (DataAccessException e) {
            throw new CreationException("Error update book");
        }

    }

    @Override
    public void delete(long id) {
        jdbcOperations.update("delete from books where id = :id", Map.of("id", id));
    }

    @Override
    public Optional<Book> getById(long id) {

        return getBook(id);
    }

    private Optional<Book> getBook(long bookId) {
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
                            Map.of("id", bookId), new BookMapper()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    private long insertBook(Book book) {
        var keyHolder = new GeneratedKeyHolder();
        var source = new MapSqlParameterSource()
                .addValue("name", book.name())
                .addValue("authorId", book.author().id())
                .addValue("genreId", book.genre().id());

        jdbcOperations.update(
                "insert into books (book_name, author_id, genre_id) values (:name, :authorId, :genreId)",
                source,
                keyHolder);

        return getIdFromKeyHolder(keyHolder);
    }

    private long getIdFromKeyHolder(GeneratedKeyHolder keyHolder) {
        Map<String, Object> keys = keyHolder.getKeys();

        if (keys == null || keys.isEmpty()) {
            return 0;
        }

        return (Long) keys.get("id");
    }
}
