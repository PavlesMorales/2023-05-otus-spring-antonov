package ru.otus.spring.homework.veiw;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.InputProvider;
import org.springframework.shell.Shell;
import ru.otus.spring.homework.TestConfig;
import ru.otus.spring.homework.domain.genre.Genre;
import ru.otus.spring.homework.service.GenreService;
import ru.otus.spring.homework.tableconstructor.GenreTableConstructor;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class GenreShellViewTest extends TestConfig {

    @Autowired
    Shell subj;

    @MockBean
    GenreService genreService;
    @MockBean
    GenreTableConstructor tableConstructor;

    @MockBean
    InputProvider inputProvider;


    @Test
    void shouldCreateGenre() throws Exception {
        String command = "genre-create";
        String parameter = "science";
        Optional<Genre> createdGenre = Optional.of(Genre.builder()
                .id(1L)
                .genreName("science")
                .build());

        String expected = """
                +--+----------+
                |ID|GENRE_NAME|
                +--+----------+
                |1 |science   |
                +--+----------+
                """;

        when(inputProvider.readInput())
                .thenReturn(() -> command + " " + parameter)
                .thenReturn(null);
        when(genreService.createGenre("science")).thenReturn(createdGenre);
        when(tableConstructor.buildGenreTable(createdGenre.get())).thenReturn(expected);

        subj.run(inputProvider);

        verify(genreService, times(1)).createGenre(parameter);
        verify(tableConstructor, times(1)).buildGenreTable(createdGenre.get());
    }

    @Test
    void notShouldCreateGenre() throws Exception {
        Optional<Genre> createdGenre = Optional.empty();

        when(inputProvider.readInput())
                .thenReturn(() -> "genre-create science")
                .thenReturn(null);
        when(genreService.createGenre("science")).thenReturn(createdGenre);

        subj.run(inputProvider);

        verify(genreService, times(1)).createGenre("science");
        verifyNoInteractions(tableConstructor);
    }

    @Test
    void shouldShowAllGenre() throws Exception {
        String command = "genre-show-all";
        String expected = """
                +--+----------+
                |ID|GENRE_NAME|
                +--+----------+
                |1 |science   |
                +--+----------+
                """;
        List<Genre> allGenres = List.of(Genre.builder().id(1L).genreName("science").build());

        when(inputProvider.readInput())
                .thenReturn(() -> command)
                .thenReturn(null);

        when(genreService.getAll()).thenReturn(allGenres);
        when(tableConstructor.buildGenreTable(allGenres)).thenReturn(expected);

        subj.run(inputProvider);


        verify(genreService, times(1)).getAll();
        verify(tableConstructor, times(1)).buildGenreTable(allGenres);
    }

    @Test
    void shouldDeleteGenre() throws Exception {
        String command = "genre-delete";
        long id = 1L;
        when(inputProvider.readInput())
                .thenReturn(() -> command + " " + id)
                .thenReturn(null);


        when(genreService.delete(id)).thenReturn(true);
        subj.run(inputProvider);

        verify(genreService, times(1)).delete(id);
        verifyNoInteractions(tableConstructor);
    }

    @Test
    void shouldUpdateGenre() throws Exception {
        String command = "genre-update";
        long id = 1L;
        String newGenreNameParameter = "science";
        Optional<Genre> updatedGenre = Optional.of(Genre.builder()
                .id(1L)
                .genreName(newGenreNameParameter)
                .build());

        String expected = """
                +--+----------+
                |ID|GENRE_NAME|
                +--+----------+
                |1 |science   |
                +--+----------+
                """;

        when(inputProvider.readInput())
                .thenReturn(() -> command + " " + id + " " + newGenreNameParameter)
                .thenReturn(null);

        when(genreService.update(id, newGenreNameParameter)).thenReturn(updatedGenre);
        when(tableConstructor.buildGenreTable(updatedGenre.get())).thenReturn(expected);
        subj.run(inputProvider);

        verify(genreService, times(1)).update(id, newGenreNameParameter);
        verify(tableConstructor, times(1)).buildGenreTable(updatedGenre.get());
    }
}