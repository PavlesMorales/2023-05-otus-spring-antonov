package ru.otus.spring.homework.veiw;

import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.homework.domain.genre.Genre;
import ru.otus.spring.homework.service.GenreService;
import ru.otus.spring.homework.tableconstructor.GenreTableConstructor;

import java.util.Optional;


@ShellComponent
@RequiredArgsConstructor
public class GenreShellView {
    private final GenreService service;

    private final GenreTableConstructor tableConstructor;

    @ShellMethod(value = "Create genre command", key = {"genre-create"})
    public String createGenre(@ShellOption @Size(min = 2, max = 40) String genreName) {
        Optional<Genre> createdGenre = service.createGenre(genreName);
        if (createdGenre.isPresent()) {
            return tableConstructor.buildGenreTable(createdGenre.get());
        } else {
            return "Genre not created";
        }
    }

    @ShellMethod(value = "Show all genre command", key = {"genre-show-all"})
    public String showAllGenre() {
        return tableConstructor.buildGenreTable(service.getAll());
    }

    @ShellMethod(value = "Get genre by id command", key = {"genre-get"})
    public String getGenre(@ShellOption Long id) {
        Optional<Genre> genre = service.get(id);
        if (genre.isPresent()) {
            return tableConstructor.buildGenreTable(genre.get());
        } else {
            return "Genre with id %s not found".formatted(id);
        }
    }

    @ShellMethod(value = "Delete genre by id command", key = {"genre-delete"})
    public String deleteGenre(@ShellOption Long id) {
        boolean deleted = service.delete(id);
        if (deleted) {
            return "Genre with id %s deleted".formatted(id);
        } else {
            return "Genre not deleted";
        }

    }

    @ShellMethod(value = "Update genre command", key = {"genre-update"})
    public String updateGenre(@ShellOption Long id,
                              @ShellOption @Size(min = 2, max = 40) String newName) {

        Optional<Genre> updatedGenre = service.update(id, newName);
        if (updatedGenre.isPresent()) {
            return tableConstructor.buildGenreTable(updatedGenre.get());
        } else {
            return "Genre not updated";
        }
    }
}
