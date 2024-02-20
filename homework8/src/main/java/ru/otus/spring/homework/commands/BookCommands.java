package ru.otus.spring.homework.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.homework.commands.converters.BookConverter;
import ru.otus.spring.homework.services.BookService;

import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class BookCommands {

    private final BookService bookService;

    private final BookConverter bookConverter;

    @ShellMethod(value = "Find all books", key = "ba")
    public String findAllBooks() {
        return bookService.findAll().stream()
                .map(bookConverter::bookToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find book by id", key = "bg")
    public String findBookById(@ShellOption String id) {
        return bookConverter.bookToString(bookService.findById(id));
    }

    @ShellMethod(value = "Create book", key = "bc")
    public String create(@ShellOption String title,
                         @ShellOption String authorId,
                         @ShellOption String genreId) {

        return bookConverter.bookToString(bookService.create(title, authorId, genreId));
    }

    @ShellMethod(value = "Update book", key = "bu")
    public String update(@ShellOption String id,
                         @ShellOption String title,
                         @ShellOption String authorId,
                         @ShellOption String genreId) {

        return bookConverter.bookToString(bookService.update(id, title, authorId, genreId));
    }

    @ShellMethod(value = "Delete book by id", key = "bd")
    public void deleteBook(@ShellOption String id) {
        bookService.deleteById(id);
    }
}
