package ru.otus.spring.homework.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.homework.commands.converters.CommentConverter;
import ru.otus.spring.homework.services.CommentService;

import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class CommentCommands {

    private final CommentConverter commentConverter;

    private final CommentService commentService;


    @ShellMethod(value = "Find all comments", key = "ca")
    public String findAllCommentsByBookId(@ShellOption Long bookId) {

        return commentService.findAllByBookId(bookId)
                .stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find comment by id", key = "cg")
    public String findCommentById(@ShellOption Long id) {

        return commentConverter.commentToString(commentService.findById(id));
    }

    @ShellMethod(value = "Create comment", key = "cc")
    public String create(@ShellOption String text,
                         @ShellOption Long bookId) {

        return commentConverter.commentToString(commentService.create(text, bookId));
    }

    @ShellMethod(value = "Update comment", key = "cu")
    public String update(@ShellOption Long id,
                         @ShellOption String text) {

        return commentConverter.commentToString(commentService.update(id, text));

    }

    @ShellMethod(value = "Delete comment", key = "cd")
    public void deleteComment(@ShellOption Long id) {
        commentService.deleteById(id);
    }

}
