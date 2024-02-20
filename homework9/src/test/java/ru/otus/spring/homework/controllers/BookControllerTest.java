package ru.otus.spring.homework.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.homework.models.dto.AuthorDto;
import ru.otus.spring.homework.models.dto.BookDto;
import ru.otus.spring.homework.models.dto.GenreDto;
import ru.otus.spring.homework.services.AuthorService;
import ru.otus.spring.homework.services.BookService;
import ru.otus.spring.homework.services.GenreService;

import java.util.List;
import java.util.stream.LongStream;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    BookService bookService;

    @MockBean
    AuthorService authorService;

    @MockBean
    GenreService genreService;

    private List<AuthorDto> dbAuthors;

    private List<GenreDto> dbGenres;

    private List<BookDto> dbBooks;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
        dbGenres = getDbGenres();
        dbBooks = getDbBooks(dbAuthors, dbGenres);
    }

    @Test
    void shouldReturnListBooks() throws Exception {

        when(bookService.findAll()).thenReturn(dbBooks);

        mvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", dbBooks))
                .andExpect(view().name("books/list"));
        verify(bookService, times(1)).findAll();
        verifyNoInteractions(authorService, genreService);
    }

    @Test
    void shouldReturnAuthorsGenresWithCreateView() throws Exception {
        when(authorService.findAll()).thenReturn(dbAuthors);
        when(genreService.findAll()).thenReturn(dbGenres);
        final BookDto emptyBook = BookDto.builder()
                .authorDto(AuthorDto.builder().build())
                .genreDto(GenreDto.builder().build())
                .build();

        mvc.perform(get("/books/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", emptyBook))
                .andExpect(model().attribute("authors", dbAuthors))
                .andExpect(model().attribute("genres", dbGenres))
                .andExpect(view().name("books/create"));

        verify(authorService, times(1)).findAll();
        verify(genreService, times(1)).findAll();
        verifyNoInteractions(bookService);

    }

    @Test
    void shouldCreateBookAndRedirectOnBooks() throws Exception {
        final BookDto bookDto = dbBooks.get(0);
        BookDto book = BookDto.builder()
                .title("sadqwe")
                .build();
        when(bookService.create(book)).thenReturn(bookDto);

        mvc.perform(post("/books/create")
                        .flashAttr("book", book))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(model().size(0))
                .andExpect(redirectedUrl("/books"));

        verify(bookService, times(1)).create(book);
        verifyNoInteractions(authorService, genreService);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a"})
    void shouldReturnValidationErrorWhenCreateBook(String invalidTitle) throws Exception {
        final BookDto bookDto = dbBooks.get(0);
        bookDto.setTitle(invalidTitle);
        when(authorService.findAll()).thenReturn(dbAuthors);
        when(genreService.findAll()).thenReturn(dbGenres);

        mvc.perform(post("/books/create")
                        .flashAttr("book", bookDto))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().size(3))
                .andExpect(model().attribute("authors", dbAuthors))
                .andExpect(model().attribute("genres", dbGenres))
                .andExpect(model().attribute("book", bookDto))
                .andExpect(view().name("books/create"));

        verify(authorService, times(1)).findAll();
        verify(genreService, times(1)).findAll();
        verifyNoInteractions(bookService);
    }

    @Test
    void shouldReturnAuthorsGenresBookWithEditView() throws Exception {
        when(bookService.findById(1L)).thenReturn(dbBooks.get(0));
        when(authorService.findAll()).thenReturn(dbAuthors);
        when(genreService.findAll()).thenReturn(dbGenres);

        mvc.perform(get("/books/edit")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", dbBooks.get(0)))
                .andExpect(model().attribute("authors", dbAuthors))
                .andExpect(model().attribute("genres", dbGenres))
                .andExpect(model().size(3))
                .andExpect(view().name("books/edit"));

        verify(authorService, times(1)).findAll();
        verify(genreService, times(1)).findAll();
        verify(bookService, times(1)).findById(1L);
    }

    @Test
    void shouldSaveEditBook() throws Exception {
        final BookDto bookDto = dbBooks.get(0);

        BookDto book = BookDto.builder()
                .id(1L)
                .title("sadqwe")
                .build();

        when(bookService.update(book)).thenReturn(bookDto);

        mvc.perform(post("/books/edit")
                        .flashAttr("book", book))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(model().size(0))
                .andExpect(redirectedUrl("/books"));

        verify(bookService, times(1)).update(book);
        verifyNoInteractions(authorService, genreService);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a"})
    void shouldReturnValidationErrorWhenUpdateBook(String invalidTitle) throws Exception {
        final BookDto bookDto = dbBooks.get(0);
        bookDto.setTitle(invalidTitle);

        when(authorService.findAll()).thenReturn(dbAuthors);
        when(genreService.findAll()).thenReturn(dbGenres);

        mvc.perform(post("/books/edit")
                        .flashAttr("book", bookDto))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().size(3))
                .andExpect(model().attribute("authors", dbAuthors))
                .andExpect(model().attribute("genres", dbGenres))
                .andExpect(model().attribute("book", bookDto))
                .andExpect(view().name("books/edit"));

        verify(authorService, times(1)).findAll();
        verify(genreService, times(1)).findAll();
        verifyNoInteractions(bookService);
    }

    @Test
    void shouldReturnDeleteViewBook() throws Exception {
        when(bookService.findById(1L)).thenReturn(dbBooks.get(0));

        mvc.perform(get("/books/delete")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", dbBooks.get(0)))
                .andExpect(model().size(1))
                .andExpect(view().name("books/delete"));

        verify(bookService, times(1)).findById(1L);
    }

    @Test
    void shouldDeleteBook() throws Exception {
        doNothing().when(bookService).deleteById(1L);

        mvc.perform(post("/books/delete")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(model().size(0))
                .andExpect(redirectedUrl("/books"));

        verify(bookService, times(1)).deleteById(1L);
        verifyNoInteractions(authorService, genreService);
    }


    private static List<AuthorDto> getDbAuthors() {
        return LongStream.range(1, 4).boxed()
                .map(id -> AuthorDto.builder()
                        .id(id)
                        .firstName("firstName_" + id)
                        .lastName("lastName_" + id)
                        .build())
                .toList();
    }

    private static List<GenreDto> getDbGenres() {
        return LongStream.range(1, 4).boxed()
                .map(id -> GenreDto.builder().id(id).name("genre_" + id).build())
                .toList();
    }

    private static List<BookDto> getDbBooks(List<AuthorDto> dbAuthors, List<GenreDto> dbGenres) {
        return LongStream.range(1, 4).boxed()
                .map(id -> BookDto.builder()
                        .id(id)
                        .title("title_" + id)
                        .authorDto(dbAuthors.get(id.intValue() - 1))
                        .genreDto(dbGenres.get(id.intValue() - 1))
                        .build())
                .toList();
    }

    private static List<BookDto> getDbBooks() {
        var dbAuthors = getDbAuthors();
        var dbGenres = getDbGenres();
        return getDbBooks(dbAuthors, dbGenres);
    }
}