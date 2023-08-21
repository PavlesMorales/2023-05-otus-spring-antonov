package ru.otus.spring.homework.dao.book;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.homework.domain.author.Author;
import ru.otus.spring.homework.domain.book.Book;
import ru.otus.spring.homework.domain.genre.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {


    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {

        return Book.builder()
                .id(rs.getLong("book_id"))
                .name(rs.getString("book_name"))
                .author(Author.builder()
                        .id(rs.getLong("author_id"))
                        .firstName(rs.getString("author_first_name"))
                        .lastName(rs.getString("author_last_name"))
                        .build())
                .genre(Genre.builder()
                        .id(rs.getLong("genre_id"))
                        .genreName(rs.getString("genre_name"))
                        .build())
                .build();
    }
}
