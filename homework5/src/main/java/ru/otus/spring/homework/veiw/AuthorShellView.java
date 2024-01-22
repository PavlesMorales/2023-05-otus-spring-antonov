package ru.otus.spring.homework.veiw;

import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.homework.domain.author.Author;
import ru.otus.spring.homework.service.AuthorService;
import ru.otus.spring.homework.tableconstructor.AuthorTableConstructor;

@ShellComponent
@RequiredArgsConstructor
public class AuthorShellView {

    private final AuthorService authorService;

    private final AuthorTableConstructor tableConstructor;

    @ShellMethod(value = "Create author command", key = "author-create")
    public String createAuthor(@ShellOption @Size(min = 2, max = 40) String firstName,
                               @ShellOption @Size(min = 2, max = 40) String lastName) {

        Author author = authorService.create(Author.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build()
        );

        return tableConstructor.createTable(author);
    }

    @ShellMethod(value = "Get all author command", key = "author-show-all")
    public String getAll() {
        return tableConstructor.createTable(authorService.getAll());
    }

    @ShellMethod(value = "Update author by id command", key = "author-update")
    public String update(@ShellOption Long id,
                         @ShellOption @Size(min = 2, max = 40) String firstName,
                         @ShellOption @Size(min = 2, max = 40) String lastName) {

        Author author = authorService.update(Author.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .build()
        );

        return tableConstructor.createTable(author);
    }

    @ShellMethod(value = "Get author by id", key = "author-get")
    public String getAuthor(@ShellOption Long id) {
        return tableConstructor.createTable(authorService.getById(id));
    }

    @ShellMethod(value = "Delete author by id command", key = "author-delete")
    public void delete(@ShellOption Long id) {
        authorService.delete(id);
    }
}
