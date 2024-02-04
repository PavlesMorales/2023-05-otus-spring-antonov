package ru.otus.spring.homework.commands;

import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.homework.commands.converters.AuthorConverter;
import ru.otus.spring.homework.services.AuthorService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class AuthorCommands {

    private final AuthorService authorService;

    private final AuthorConverter authorConverter;

    @ShellMethod(value = "Find all authors", key = "aa")
    public String findAllAuthors() {
        return authorService.findAll().stream()
                .map(authorConverter::authorToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Update author by id command", key = "au")
    public String update(@ShellOption Long id,
                         @ShellOption @Size(min = 2, max = 40) String firstName,
                         @ShellOption @Size(min = 2, max = 40) String lastName) {

        return authorConverter.authorToString(authorService.update(id, firstName, lastName));

    }

    @ShellMethod(value = "Create author command", key = "ac")
    public String create(@ShellOption @Size(min = 2, max = 40) String firstName,
                         @ShellOption @Size(min = 2, max = 40) String lastName) {

        return authorConverter.authorToString(authorService.insert(firstName, lastName));
    }

    @ShellMethod(value = "Get author by id", key = "ag")
    public String getAuthor(@ShellOption Long id) {
        return authorConverter.authorToString(authorService.findById(id));
    }

    @ShellMethod(value = "Delete author by id command", key = "ad")
    public void delete(@ShellOption Long id) {
        authorService.deleteById(id);
    }
}
