package ru.otus.spring.homework.tableconstructor;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.homework.domain.genre.Genre;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreTableConstructorImpl implements GenreTableConstructor {

    private static final String ID_HEADER = "ID";

    private static final String GENRE_NAME_HEADER = "GENRE_NAME";

    private static final int FIELDS_TO_VIEW_COUNT = 2;

    private final TableConstructor tableConstructor;


    @Override
    public String buildGenreTable(List<Genre> genres) {
        Object[][] objects = new Object[genres.size() + 1][FIELDS_TO_VIEW_COUNT];

        objects[0] = new Object[]{ID_HEADER, GENRE_NAME_HEADER};

        for (int idx = 0, j = 1; idx < genres.size(); idx++, j++) {
            Genre genre = genres.get(idx);
            objects[j][0] = genre.id();
            objects[j][1] = genre.genreName();
        }

        return tableConstructor.buildTable(objects);
    }


    @Override
    public String buildGenreTable(Genre genre) {
        return buildGenreTable(List.of(genre));
    }
}