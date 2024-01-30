package ru.otus.spring.homework.veiw;

import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.homework.domain.genre.Genre;
import ru.otus.spring.homework.service.GenreService;
import ru.otus.spring.homework.tableconstructor.GenreTableConstructor;


@ShellComponent
@RequiredArgsConstructor
public class GenreShellView {
    private final GenreService service;

    private final GenreTableConstructor tableConstructor;

    @ShellMethod(value = "Create genre command", key = {"genre-create"})
    public String createGenre(@ShellOption @Size(min = 2, max = 40) String genreName) {
        Genre genre = service.create(Genre.builder()
                .genreName(genreName)
                .build()
        );

        return tableConstructor.buildGenreTable(genre);
    }

    @ShellMethod(value = "Show all genre command", key = {"genre-show-all"})
    public String showAllGenre() {
        return tableConstructor.buildGenreTable(service.getAll());
    }

    @ShellMethod(value = "Get genre by id command", key = {"genre-get"})
    public String getGenre(@ShellOption Long id) {
        Genre genre = service.getById(id);
        return tableConstructor.buildGenreTable(genre);
    }

    @ShellMethod(value = "Delete genre by id command", key = {"genre-delete"})
    public void deleteGenre(@ShellOption Long id) {
        service.delete(id);
    }

    @ShellMethod(value = "Update genre command", key = {"genre-update"})
    public String updateGenre(@ShellOption Long id,
                              @ShellOption @Size(min = 2, max = 40) String newName) {

        Genre genre = service.update(Genre.builder()
                .id(id)
                .genreName(newName)
                .build());

        return tableConstructor.buildGenreTable(genre);
    }
}
