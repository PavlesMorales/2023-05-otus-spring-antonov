package ru.otus.spring.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.homework.dao.author.AuthorDao;
import ru.otus.spring.homework.dao.book.BookDao;
import ru.otus.spring.homework.dao.genre.GenreDao;
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
    BookDao bookDao;
    @Mock
    AuthorDao authorDao;
    @Mock
    GenreDao genreDao;

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

        when(authorDao.getById(1L)).thenReturn(Optional.of(author));
        when(genreDao.getById(1L)).thenReturn(Optional.of(genre));
        when(bookDao.save(book)).thenReturn(expected);

        Book actual = subj.createBook("book", 1L, 1L);

        assertThat(actual).isEqualTo(expected);

        verify(authorDao, times(1)).getById(1L);
        verify(genreDao, times(1)).getById(1L);
        verify(bookDao, times(1)).save(book);

    }

    @Test
    void shouldThrownNotFoundExceptionAuthor() {
        when(authorDao.getById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> subj.createBook("book", 1L, 1L))
                .isInstanceOf(NotFoundException.class);


        verify(authorDao, times(1)).getById(1L);

    }

    @Test
    void shouldThrownNotFoundExceptionGenre() {
        Author author = Author.builder()
                .id(1L)
                .firstName("first_name")
                .lastName("last_name")
                .build();
        when(authorDao.getById(1L)).thenReturn(Optional.of(author));
        when(genreDao.getById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> subj.createBook("book", 1L, 1L))
                .isInstanceOf(NotFoundException.class);


        verify(authorDao, times(1)).getById(1L);
        verify(genreDao, times(1)).getById(1L);

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

        when(bookDao.getById(1L)).thenReturn(Optional.of(expected));

        Book actual = subj.getBook(1L);

        assertThat(expected).isEqualTo(actual);

        verify(bookDao, times(1)).getById(1L);
        verifyNoMoreInteractions(authorDao, genreDao);

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

        when(bookDao.getById(1L)).thenReturn(Optional.of(bookFromDb));
        when(authorDao.getById(3L)).thenReturn(Optional.of(authorFromDb));
        when(genreDao.getById(3L)).thenReturn(Optional.of(genreFromDb));
        when(bookDao.save(expected)).thenReturn(expected);

        Book actual = subj.updateBook(expected);

        assertThat(expected).isEqualTo(actual);

        verify(bookDao, times(1)).getById(1L);
        verify(authorDao, times(1)).getById(3L);
        verify(genreDao, times(1)).getById(3L);
        verify(bookDao, times(1)).save(expected);

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

        when(bookDao.getAll()).thenReturn(expected);

        List<Book> actual = subj.getAllBook();

        assertThat(expected).isEqualTo(actual);

        verify(bookDao, times(1)).getAll();
        verifyNoMoreInteractions(authorDao, genreDao);
    }

    @Test
    void deleteBook() {
        doNothing().when(bookDao).deleteById(1L);

        subj.deleteBook(1L);

        verify(bookDao, times(1)).deleteById(1L);
        verifyNoMoreInteractions(authorDao, genreDao);
    }
}