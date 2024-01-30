package ru.otus.spring.homework.tableconstructor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.homework.domain.book.Book;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookTableConstructorImpl implements BookTableConstructor {
    private static final String ID_HEADER = "ID";

    private static final String BOOK_NAME_HEADER = "BOOK_NAME";

    private static final String GENRE_NAME_HEADER = "GENRE_NAME";

    private static final String AUTHOR_FIRST_NAME_HEADER = "AUTHOR_FIRST_NAME";

    private static final String AUTHOR_LAST_NAME_HEADER = "AUTHOR_LAST_NAME";

    private static final int FIELDS_TO_VIEW_COUNT = 5;


    private final TableConstructor tableConstructor;

    @Override
    public String buildBookTable(Book book) {
        return buildBookTable(List.of(book));
    }

    @Override
    public String buildBookTable(List<Book> books) {
        Object[][] objects = new Object[books.size() + 1][FIELDS_TO_VIEW_COUNT];
        objects[0] = new Object[]{
                ID_HEADER,
                BOOK_NAME_HEADER,
                GENRE_NAME_HEADER,
                AUTHOR_FIRST_NAME_HEADER,
                AUTHOR_LAST_NAME_HEADER};

        for (int idx = 0, j = 1; idx < books.size(); idx++, j++) {
            Book book = books.get(idx);
            objects[j][0] = book.id();
            objects[j][1] = book.name();
            objects[j][2] = book.genre().genreName();
            objects[j][3] = book.author().firstName();
            objects[j][4] = book.author().lastName();
        }
        return tableConstructor.buildTable(objects);
    }
}
