package ru.otus.spring.homework.veiw;

import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.homework.domain.author.Author;
import ru.otus.spring.homework.service.AuthorService;
import ru.otus.spring.homework.tableconstructor.AuthorTableConstructor;

import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class AuthorShellView {

    private final AuthorService authorService;

    private final AuthorTableConstructor tableConstructor;

    @ShellMethod(value = "Create author command", key = "author-create")
    public String createAuthor(@ShellOption @Size(min = 2, max = 40) String firstName,
                               @ShellOption @Size(min = 2, max = 40) String lastName) {

        Optional<Author> author = authorService.create(firstName, lastName);
        if (author.isPresent()) {
            return tableConstructor.createTable(author.get());
        } else {
            return "Author not created";
        }
    }

    @ShellMethod(value = "Get all author command", key = "author-show-all")
    public String getAll() {
        return tableConstructor.createTable(authorService.getAll());
    }

    @ShellMethod(value = "Update author by id command", key = "author-update")
    public String update(@ShellOption Long id,
                         @ShellOption @Size(min = 2, max = 40) String firstName,
                         @ShellOption @Size(min = 2, max = 40) String lastName) {
        Optional<Author> authorOptional = authorService.update(id, firstName, lastName);
        if (authorOptional.isPresent()) {
            return tableConstructor.createTable(authorOptional.get());
        } else {
            return "Author not update";
        }
    }

    @ShellMethod(value = "Get author by id", key = "author-get")
    public String getAuthor(@ShellOption Long id) {
        Optional<Author> authorOptional = authorService.getById(id);
        if (authorOptional.isPresent()) {
            return tableConstructor.createTable(authorOptional.get());
        } else {
            return "Author not found";
        }
    }

    @ShellMethod(value = "Delete author by id command", key = "author-delete")
    public String delete(@ShellOption Long id) {
        boolean deleted = authorService.delete(id);
        if (deleted) {
            return "Author with id %s deleted".formatted(id);
        } else {
            return "Author with id not deleted";
        }
    }
}
