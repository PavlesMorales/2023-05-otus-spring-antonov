package ru.otus.spring.homework.veiw;

import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.homework.domain.book.Book;
import ru.otus.spring.homework.service.BookService;
import ru.otus.spring.homework.tableconstructor.BookTableConstructor;

import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class BookShellView {

    private final BookService bookService;

    private final BookTableConstructor bookTableConstructor;

    @ShellMethod(value = "Create book command", key = "book-create")
    public String createBook(@ShellOption @Size(min = 2, max = 40) String bookName,
                             @ShellOption long authorId,
                             @ShellOption long genreId) {
        Optional<Book> book = bookService.createBook(bookName, authorId, genreId);
        if (book.isPresent()) {
            return bookTableConstructor.buildBookTable(book.get());
        }
        return "Book not created";
    }

    @ShellMethod(value = "Show all books", key = "book-show-all")
    public String showAllBook() {
        return bookTableConstructor.buildBookTable(bookService.getAllBook());
    }

    @ShellMethod(value = "Update book by id command", key = "book-update")
    public String updateBook(@ShellOption Long id, @Size(min = 2, max = 40) String newName) {
        Optional<Book> updatedBook = bookService.updateName(id, newName);
        if (updatedBook.isPresent()) {
            return bookTableConstructor.buildBookTable(updatedBook.get());
        } else {
            return "Book with id = %s not updated".formatted(id);
        }
    }

    @ShellMethod(value = "Delete book by id command", key = "book-delete")
    public String deleteBook(@ShellOption Long id) {
        boolean isDeleted = bookService.deleteBook(id);
        if (isDeleted) {
            return "Book with id = %s was deleted".formatted(id);
        } else {
            return "Book with id = %s not deleted".formatted(id);
        }
    }

    @ShellMethod(value = "Get book by id command", key = "book-get")
    public String getBook(@ShellOption Long id) {
        Optional<Book> book = bookService.getBook(id);
        if (book.isPresent()) {
            return bookTableConstructor.buildBookTable(book.get());
        } else {
            return "Book with id = %s not found";
        }
    }
}
