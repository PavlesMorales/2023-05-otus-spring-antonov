package ru.otus.spring.homework.commands;

import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.homework.commands.converters.GenreConverter;
import ru.otus.spring.homework.services.GenreService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class GenreCommands {

    private final GenreService genreService;

    private final GenreConverter genreConverter;

    @ShellMethod(value = "Find all genres", key = "ga")
    public String findAllGenres() {
        return genreService.findAll().stream()
                .map(genreConverter::genreToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Create genre command", key = "gc")
    public String create(@ShellOption @Size(min = 2, max = 40) String name) {

        return genreConverter.genreToString(genreService.insert(name));
    }

    @ShellMethod(value = "Get genre by id command", key = "gg")
    public String getGenre(@ShellOption Long id) {
        return genreConverter.genreToString(genreService.findById(id));
    }

    @ShellMethod(value = "Delete genre by id command", key = "gd")
    public void deleteGenre(@ShellOption Long id) {
        genreService.deleteById(id);
    }

    @ShellMethod(value = "Update genre command", key = "gu")
    public String update(@ShellOption Long id,
                         @ShellOption @Size(min = 2, max = 40) String name) {

        return genreConverter.genreToString(genreService.update(id, name));
    }
}
