package ru.otus.spring.homework.dao.genre;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.homework.domain.genre.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreMapper implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {

        return Genre.builder()
                .id(rs.getLong("id"))
                .genreName(rs.getString("genre_name"))
                .build();
    }
}
