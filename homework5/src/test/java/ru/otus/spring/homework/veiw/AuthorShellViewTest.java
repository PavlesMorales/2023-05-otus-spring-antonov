package ru.otus.spring.homework.veiw;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.InputProvider;
import org.springframework.shell.Shell;
import ru.otus.spring.homework.TestConfig;
import ru.otus.spring.homework.domain.author.Author;
import ru.otus.spring.homework.service.AuthorService;
import ru.otus.spring.homework.tableconstructor.AuthorTableConstructor;

import java.util.List;

import static org.mockito.Mockito.*;

class AuthorShellViewTest extends TestConfig {

    @Autowired
    Shell subj;

    @MockBean
    AuthorService service;

    @MockBean
    AuthorTableConstructor tableConstructor;

    @MockBean
    InputProvider inputProvider;

    @Test
    void shouldCreateAuthor() throws Exception {
        String command = "author-create";
        String parameterFirst = "first_name";
        String parameterSecond = "second_name";

        Author author = Author.builder()
                .firstName(parameterFirst)
                .lastName(parameterSecond)
                .build();

        Author savedAuthor = Author.builder()
                .id(1L)
                .firstName(parameterFirst)
                .lastName(parameterSecond)
                .build();
        String expected = "success show create author";

        when(inputProvider.readInput())
                .thenReturn(() -> "%s %s %s" .formatted(command, parameterFirst, parameterSecond))
                .thenReturn(null);
        when(service.create(author)).thenReturn(savedAuthor);
        when(tableConstructor.createTable(savedAuthor)).thenReturn(expected);

        subj.run(inputProvider);

        verify(service, times(1)).create(author);
        verify(tableConstructor, times(1)).createTable(savedAuthor);
    }

    @Test
    void shouldGetAllAuthors() throws Exception {
        String command = "author-show-all";

        Author authorFirst = Author.builder()
                .id(1L)
                .firstName("first_name")
                .lastName("second_name")
                .build();

        Author authorSecond = Author.builder()
                .id(2L)
                .firstName("first_name_2")
                .lastName("second_name_2")
                .build();

        List<Author> authors = List.of(authorFirst, authorSecond);
        String expected = "success show all authors";

        when(inputProvider.readInput())
                .thenReturn(() -> command)
                .thenReturn(null);
        when(service.getAll()).thenReturn(authors);
        when(tableConstructor.createTable(authors)).thenReturn(expected);

        subj.run(inputProvider);

        verify(service, times(1)).getAll();
        verify(tableConstructor, times(1)).createTable(authors);
    }

    @Test
    void shouldUpdateAuthor() throws Exception {
        String command = "author-update";
        Long parameterFirst = 1L;
        String parameterSecond = "first_name_up";
        String parameterThird = "second_name_up";

        Author author = Author.builder()
                .id(parameterFirst)
                .firstName(parameterSecond)
                .lastName(parameterThird)
                .build();

        Author updatedAuthor = Author.builder()
                .id(parameterFirst)
                .firstName(parameterSecond)
                .lastName(parameterThird)
                .build();
        String expected = "success show updated author";

        when(inputProvider.readInput())
                .thenReturn(() -> "%s %s %s %s".formatted(command, parameterFirst, parameterSecond, parameterThird))
                .thenReturn(null);
        when(service.update(author)).thenReturn(updatedAuthor);
        when(tableConstructor.createTable(updatedAuthor)).thenReturn(expected);

        subj.run(inputProvider);

        verify(service, times(1)).update(author);
        verify(tableConstructor, times(1)).createTable(updatedAuthor);
    }

    @Test
    void shouldGetAuthor() throws Exception {
        String command = "author-get";
        Long parameterFirst = 1L;
        String firstName = "first_name_up";
        String secondName = "second_name_up";

        Author author = Author.builder()
                .id(parameterFirst)
                .firstName(firstName)
                .lastName(secondName)
                .build();

        String expected = "success show get author";

        when(inputProvider.readInput())
                .thenReturn(() -> "%s %s".formatted(command, parameterFirst))
                .thenReturn(null);
        when(service.getById(parameterFirst)).thenReturn(author);
        when(tableConstructor.createTable(author)).thenReturn(expected);

        subj.run(inputProvider);

        verify(service, times(1)).getById(parameterFirst);
        verify(tableConstructor, times(1)).createTable(author);
    }

    @Test
    void shouldDeleteAuthor() throws Exception {
        String command = "author-delete";
        long parameterFirst = 1L;

        when(inputProvider.readInput())
                .thenReturn(() -> "%s %s".formatted(command, parameterFirst))
                .thenReturn(null);

        doNothing().when(service).delete(parameterFirst);

        subj.run(inputProvider);

        verify(service, times(1)).delete(parameterFirst);
        verifyNoInteractions(tableConstructor);
    }
}