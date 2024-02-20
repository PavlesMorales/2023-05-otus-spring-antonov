package ru.otus.spring.homework.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.homework.models.dto.BookDto;
import ru.otus.spring.homework.models.dto.CommentDto;
import ru.otus.spring.homework.services.BookService;
import ru.otus.spring.homework.services.CommentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final BookService bookService;

    @GetMapping("/comments/pick-books")
    public String listComments(Model model) {
        final List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        model.addAttribute("comment", CommentDto.builder().build());
        return "comments/list";
    }

    @GetMapping("comments")
    public String listComments(@RequestParam("bookId") long id, Model model) {
        final List<CommentDto> comments = commentService.findAllByBookId(id);
        final List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        model.addAttribute("bookId", id);
        model.addAttribute("comments", comments);
        model.addAttribute("comment", CommentDto.builder().build());
        return "comments/list";
    }

    @GetMapping("/comments/edit")
    public String editComment(@RequestParam("id") long id, Model model) {
        final CommentDto comment = commentService.findById(id);
        model.addAttribute("comment", comment);
        return "comments/edit";
    }

    @PostMapping("/comments/edit")
    public String editComment(@Valid @ModelAttribute CommentDto commentDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "comments/edit";
        }
        commentService.update(commentDto);
        return "redirect:/comments/pick-books";
    }

    @GetMapping("/comments/create")
    public String createComment(@RequestParam("bookId") long id, Model model) {
        model.addAttribute("comment", CommentDto.builder().bookId(id).build());
        return "comments/create";
    }

    @PostMapping("/comments/create")
    public String createComment(@Valid @ModelAttribute CommentDto commentDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "comments/edit";
        }
        commentService.create(commentDto);
        return "redirect:/comments/pick-books";
    }

    @GetMapping("comments/delete")
    public String deleteComment(@RequestParam("id") long id, Model model) {
        final CommentDto comment = commentService.findById(id);
        model.addAttribute("comment", comment);
        return "comments/delete";
    }

    @PostMapping("/comments/delete")
    public String deleteComment(@RequestParam("id") long id) {
        commentService.deleteById(id);
        return "redirect:/comments/pick-books";
    }

}
