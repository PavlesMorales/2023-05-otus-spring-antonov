package ru.otus.spring.homework.veiw;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.InputProvider;
import org.springframework.shell.Shell;
import ru.otus.spring.homework.TestConfig;
import ru.otus.spring.homework.domain.author.Author;
import ru.otus.spring.homework.domain.book.Book;
import ru.otus.spring.homework.domain.genre.Genre;
import ru.otus.spring.homework.service.AuthorService;
import ru.otus.spring.homework.service.BookService;
import ru.otus.spring.homework.tableconstructor.AuthorTableConstructor;
import ru.otus.spring.homework.tableconstructor.BookTableConstructor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class BookShellViewTest extends TestConfig {

    @Autowired
    Shell subj;

    @MockBean
    BookService service;

    @MockBean
    BookTableConstructor tableConstructor;

    @MockBean
    InputProvider inputProvider;

    @Test
    void shouldCreateBook() throws Exception {
        String command = "book-create";
        String parameterFirst = "book_name";
        Long parameterSecond = 1L;
        Long parameterThird = 1L;

        Author author = Author.builder()
                .id(1L)
                .firstName("first_name")
                .lastName("second_name")
                .build();

        Genre genre = Genre.builder()
                .id(1L)
                .genreName("genreName")
                .build();
        Book createdBook = Book.builder()
                .id(1L)
                .genre(genre)
                .author(author)
                .build();
        String expected = "success show create book";

        when(inputProvider.readInput())
                .thenReturn(() -> "%s %s %s %s".formatted(command, parameterFirst, parameterSecond, parameterThird))
                .thenReturn(null);

        when(service.createBook(parameterFirst, parameterSecond, parameterThird)).thenReturn(createdBook);

        when(tableConstructor.buildBookTable(createdBook)).thenReturn(expected);

        subj.run(inputProvider);

        verify(service, times(1)).createBook(parameterFirst, parameterSecond, parameterThird);
        verify(tableConstructor, times(1)).buildBookTable(createdBook);
    }

    @Test
    void showAllBook() throws Exception {
        String command = "book-show-all";

        Author author = Author.builder()
                .id(1L)
                .firstName("first_name")
                .lastName("second_name")
                .build();

        Genre genre = Genre.builder()
                .id(1L)
                .genreName("genreName")
                .build();

        Book createdBook = Book.builder()
                .id(1L)
                .genre(genre)
                .author(author)
                .build();
        List<Book> books = List.of(createdBook);

        String expected = "success show all book";

        when(inputProvider.readInput())
                .thenReturn(() -> command)
                .thenReturn(null);

        when(service.getAllBook()).thenReturn(books);

        when(tableConstructor.buildBookTable(books)).thenReturn(expected);

        subj.run(inputProvider);

        verify(service, times(1)).getAllBook();
        verify(tableConstructor, times(1)).buildBookTable(books);
    }

    @Test
    void shouldUpdateBook() throws Exception {
        String command = "book-update";
        Long parameterFirst = 1L;
        String parameterSecond = "book_name";
        Long parameterThird = 3L;
        Long parameterFourth = 3L;

        Author author = Author.builder()
                .id(3L)
                .firstName("first_name")
                .lastName("second_name")
                .build();

        Genre genre = Genre.builder()
                .id(3L)
                .genreName("genreName")
                .build();

        Book book = Book.builder()
                .id(1L)
                .name(parameterSecond)
                .genre(Genre.builder()
                        .id(3L)
                        .build())
                .author(Author.builder()
                        .id(3L)
                        .build())
                .build();

        Book updatedBook = Book.builder()
                .id(1L)
                .name(parameterSecond)
                .genre(genre)
                .author(author)
                .build();
        String expected = "success show updated book";

        when(inputProvider.readInput())
                .thenReturn(() -> "%s %s %s %s %s"
                        .formatted(command, parameterFirst, parameterSecond, parameterThird, parameterFourth))
                .thenReturn(null);

        when(service.updateBook(book)).thenReturn(updatedBook);

        when(tableConstructor.buildBookTable(updatedBook)).thenReturn(expected);

        subj.run(inputProvider);

        verify(service, times(1)).updateBook(book);
        verify(tableConstructor, times(1)).buildBookTable(updatedBook);
    }

    @Test
    void shouldDeleteBook() throws Exception {
        String command = "book-delete";
        Long parameterFirst = 1L;

        when(inputProvider.readInput())
                .thenReturn(() -> "%s %s"
                        .formatted(command, parameterFirst))
                .thenReturn(null);

        doNothing().when(service).deleteBook(parameterFirst);

        subj.run(inputProvider);

        verify(service, times(1)).deleteBook(parameterFirst);
        verifyNoMoreInteractions(tableConstructor);

    }

    @Test
    void getBookById() throws Exception {
        String command = "book-get";
        Long parameterFirst = 1L;

        Author author = Author.builder()
                .id(3L)
                .firstName("first_name")
                .lastName("second_name")
                .build();

        Genre genre = Genre.builder()
                .id(3L)
                .genreName("genreName")
                .build();

        Book book = Book.builder()
                .id(1L)
                .name("book_name")
                .genre(genre)
                .author(author)
                .build();
        String expected = "success show book";

        when(inputProvider.readInput())
                .thenReturn(() -> "%s %s"
                        .formatted(command, parameterFirst))
                .thenReturn(null);

        when(service.getBook(1L)).thenReturn(book);

        when(tableConstructor.buildBookTable(book)).thenReturn(expected);

        subj.run(inputProvider);

        verify(service, times(1)).getBook(1L);
        verify(tableConstructor, times(1)).buildBookTable(book);
    }
}