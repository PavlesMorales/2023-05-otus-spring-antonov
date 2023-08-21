package ru.otus.spring.homework.tableconstructor;

import ru.otus.spring.homework.domain.book.Book;

import java.util.List;

public interface BookTableConstructor {

    String buildBookTable(Book book);

    String buildBookTable(List<Book> books);
}
