package ru.otus.spring.homework.tableconstructor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.homework.domain.author.Author;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorTableConstructorImpl implements AuthorTableConstructor {
    private static final String ID_HEADER = "ID";

    private static final String AUTHOR_FIRST_NAME_HEADER = "AUTHOR_FIRST_NAME";

    private static final String AUTHOR_LAST_NAME_HEADER = "AUTHOR_LAST_NAME";

    private static final int FIELDS_TO_VIEW_COUNT = 3;

    private final TableConstructor tableConstructor;

    @Override
    public String createTable(List<Author> authors) {
        Object[][] objects = new Object[authors.size() + 1][FIELDS_TO_VIEW_COUNT];
        objects[0] = new Object[]{ID_HEADER, AUTHOR_FIRST_NAME_HEADER, AUTHOR_LAST_NAME_HEADER};
        for (int idx = 0, j = 1; idx < authors.size(); idx++, j ++) {
            Author author = authors.get(idx);
            objects[j][0] = author.id();
            objects[j][1] = author.firstName();
            objects[j][2] = author.lastName();
        }
        return tableConstructor.buildTable(objects);
    }

    @Override
    public String createTable(Author author) {
        return createTable(List.of(author));
    }
}
