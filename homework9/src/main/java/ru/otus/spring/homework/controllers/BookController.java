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
import ru.otus.spring.homework.models.dto.AuthorDto;
import ru.otus.spring.homework.models.dto.BookDto;
import ru.otus.spring.homework.models.dto.GenreDto;
import ru.otus.spring.homework.services.AuthorService;
import ru.otus.spring.homework.services.BookService;
import ru.otus.spring.homework.services.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;


    @GetMapping("/books")
    public String listBook(Model model) {
        final List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books/list";
    }


    @GetMapping("/books/create")
    public String createBook(Model model) {
        final BookDto emptyBook = BookDto.builder().build();
        fillModelAuthorsAndGenresAndBook(model, emptyBook);
        return "books/create";
    }

    @PostMapping("/books/create")
    public String createBook(@Valid @ModelAttribute("book") BookDto bookDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            fillModelAuthorsAndGenresAndBook(model, bookDto);
            return "books/create";
        }

        bookService.create(bookDto);
        return "redirect:/books";
    }

    @GetMapping("/books/edit")
    public String editBook(@RequestParam("id") long id, Model model) {
        final BookDto book = bookService.findById(id);
        fillModelAuthorsAndGenresAndBook(model, book);
        return "books/edit";
    }

    @PostMapping("/books/edit")
    public String editBook(@Valid @ModelAttribute("book") BookDto bookDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            fillModelAuthorsAndGenresAndBook(model, bookDto);
            return "books/edit";
        }

        bookService.update(bookDto);
        return "redirect:/books";
    }

    @GetMapping("/books/delete")
    public String deleteBook(@RequestParam("id") long id, Model model) {
        final BookDto book = bookService.findById(id);
        model.addAttribute("book", book);
        return "books/delete";
    }

    @PostMapping("/books/delete")
    public String deleteBook(@RequestParam("id") long id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    private void fillModelAuthorsAndGenresAndBook(final Model model, BookDto bookDto) {
        final List<AuthorDto> authors = authorService.findAll();
        final List<GenreDto> genres = genreService.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("book", bookDto);
    }
}
