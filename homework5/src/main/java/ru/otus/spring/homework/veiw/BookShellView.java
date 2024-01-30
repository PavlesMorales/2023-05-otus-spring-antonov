package ru.otus.spring.homework.veiw;

import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.homework.domain.author.Author;
import ru.otus.spring.homework.domain.book.Book;
import ru.otus.spring.homework.domain.genre.Genre;
import ru.otus.spring.homework.service.BookService;
import ru.otus.spring.homework.tableconstructor.BookTableConstructor;

import static org.springframework.shell.standard.ShellOption.NULL;

@ShellComponent
@RequiredArgsConstructor
public class BookShellView {

    private final BookService bookService;

    private final BookTableConstructor bookTableConstructor;

    @ShellMethod(value = "Create book command", key = "book-create")
    public String createBook(@ShellOption @Size(min = 2, max = 40) final String bookName,
                             @ShellOption final Long authorId,
                             @ShellOption final Long genreId) {

        final Book book = bookService.createBook(bookName, authorId, genreId);
        return bookTableConstructor.buildBookTable(book);
    }

    @ShellMethod(value = "Show all books", key = "book-show-all")
    public String showAllBook() {
        return bookTableConstructor.buildBookTable(bookService.getAllBook());
    }

    @ShellMethod(value = "Update book by id command", key = "book-update")
    public String updateBook(@ShellOption Long id,
                             @Size(min = 2, max = 40) String newName,
                             @ShellOption(defaultValue = NULL) Long authorId,
                             @ShellOption(defaultValue = NULL) Long genreId) {

        final Book updatedBook = bookService.updateBook(Book.builder()
                .id(id)
                .name(newName)
                .author(Author.builder()
                        .id(authorId)
                        .build())
                .genre(Genre.builder()
                        .id(genreId)
                        .build())
                .build());

        return bookTableConstructor.buildBookTable(updatedBook);
    }

    @ShellMethod(value = "Delete book by id command", key = "book-delete")
    public void deleteBook(@ShellOption Long id) {
        bookService.deleteBook(id);
    }

    @ShellMethod(value = "Get book by id command", key = "book-get")
    public String getBook(@ShellOption Long id) {
        Book book = bookService.getBook(id);
        return bookTableConstructor.buildBookTable(book);
    }
}
