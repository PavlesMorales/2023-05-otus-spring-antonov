package ru.otus.spring.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.homework.dao.author.AuthorRepository;
import ru.otus.spring.homework.dao.book.BookRepository;
import ru.otus.spring.homework.dao.genre.GenreRepository;
import ru.otus.spring.homework.domain.author.Author;
import ru.otus.spring.homework.domain.book.Book;
import ru.otus.spring.homework.domain.genre.Genre;
import ru.otus.spring.homework.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    BookRepository bookRepository;
    @Mock
    AuthorRepository authorRepository;
    @Mock
    GenreRepository genreRepository;

    @InjectMocks
    BookServiceImpl subj;

    @Test
    void shouldCreateBook() {
        Author author = Author.builder()
                .id(1L)
                .firstName("first_name")
                .lastName("last_name")
                .build();

        Genre genre = Genre.builder()
                .id(1L)
                .genreName("Genre")
                .build();

        Book expected = Book.builder()
                .name("book")
                .author(author)
                .genre(genre)
                .build();

        Book book = Book.builder()
                .name("book")
                .author(author)
                .genre(genre)
                .build();

        when(authorRepository.getById(1L)).thenReturn(Optional.of(author));
        when(genreRepository.getById(1L)).thenReturn(Optional.of(genre));
        when(bookRepository.save(book)).thenReturn(expected);

        Book actual = subj.createBook("book", 1L, 1L);

        assertThat(actual).isEqualTo(expected);

        verify(authorRepository, times(1)).getById(1L);
        verify(genreRepository, times(1)).getById(1L);
        verify(bookRepository, times(1)).save(book);

    }

    @Test
    void shouldThrownNotFoundExceptionAuthor() {
        when(authorRepository.getById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> subj.createBook("book", 1L, 1L))
                .isInstanceOf(NotFoundException.class);


        verify(authorRepository, times(1)).getById(1L);

    }

    @Test
    void shouldThrownNotFoundExceptionGenre() {
        Author author = Author.builder()
                .id(1L)
                .firstName("first_name")
                .lastName("last_name")
                .build();
        when(authorRepository.getById(1L)).thenReturn(Optional.of(author));
        when(genreRepository.getById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> subj.createBook("book", 1L, 1L))
                .isInstanceOf(NotFoundException.class);


        verify(authorRepository, times(1)).getById(1L);
        verify(genreRepository, times(1)).getById(1L);

    }

    @Test
    void shouldReturnBook() {
        Author author = Author.builder()
                .id(1L)
                .firstName("first_name")
                .lastName("last_name")
                .build();

        Genre genre = Genre.builder()
                .id(1L)
                .genreName("Genre")
                .build();


        Book expected = Book.builder()
                .name("book")
                .author(author)
                .genre(genre)
                .build();

        when(bookRepository.getById(1L)).thenReturn(Optional.of(expected));

        Book actual = subj.getBook(1L);

        assertThat(expected).isEqualTo(actual);

        verify(bookRepository, times(1)).getById(1L);
        verifyNoMoreInteractions(authorRepository, genreRepository);

    }

    @Test
    void shouldUpdateBook() {
        Author authorFromDb = Author.builder()
                .id(3L)
                .firstName("first_name")
                .lastName("last_name")
                .build();

        Genre genreFromDb = Genre.builder()
                .id(3L)
                .genreName("Genre")
                .build();

        Author author = Author.builder()
                .id(3L)
                .firstName("another_first_name")
                .lastName("another_last_name")
                .build();

        Genre genre = Genre.builder()
                .id(3L)
                .genreName("another_genre_name")
                .build();

        Book bookFromDb = Book.builder()
                .id(1L)
                .name("book")
                .author(author)
                .genre(genre)
                .build();


        Book expected = Book.builder()
                .id(1L)
                .name("book_2")
                .author(authorFromDb)
                .genre(genreFromDb)
                .build();

        when(bookRepository.getById(1L)).thenReturn(Optional.of(bookFromDb));
        when(authorRepository.getById(3L)).thenReturn(Optional.of(authorFromDb));
        when(genreRepository.getById(3L)).thenReturn(Optional.of(genreFromDb));
        when(bookRepository.save(expected)).thenReturn(expected);

        Book actual = subj.updateBook(expected);

        assertThat(expected).isEqualTo(actual);

        verify(bookRepository, times(1)).getById(1L);
        verify(authorRepository, times(1)).getById(3L);
        verify(genreRepository, times(1)).getById(3L);
        verify(bookRepository, times(1)).save(expected);

    }

    @Test
    void shouldReturnListBook() {
        Author author = Author.builder()
                .id(3L)
                .firstName("another_first_name")
                .lastName("another_last_name")
                .build();

        Genre genre = Genre.builder()
                .id(3L)
                .genreName("another_genre_name")
                .build();

        Book book = Book.builder()
                .id(1L)
                .name("book")
                .author(author)
                .genre(genre)
                .build();

        List<Book> expected = List.of(book);

        when(bookRepository.getAll()).thenReturn(expected);

        List<Book> actual = subj.getAllBook();

        assertThat(expected).isEqualTo(actual);

        verify(bookRepository, times(1)).getAll();
        verifyNoMoreInteractions(authorRepository, genreRepository);
    }

    @Test
    void deleteBook() {
        doNothing().when(bookRepository).deleteById(1L);

        subj.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
        verifyNoMoreInteractions(authorRepository, genreRepository);
    }
}