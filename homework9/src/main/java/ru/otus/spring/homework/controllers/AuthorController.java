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
import ru.otus.spring.homework.services.AuthorService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;


    @GetMapping("/authors")
    public String findAllAuthors(Model model) {
        final List<AuthorDto> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "authors/list";
    }

    @GetMapping("/authors/create")
    public String createAuthor(Model model) {
        model.addAttribute("author", AuthorDto.builder().build());
        return "authors/create";
    }

    @PostMapping("/authors/create")
    public String createAuthor(@Valid @ModelAttribute("author") AuthorDto author,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "authors/create";
        }
        authorService.create(author);
        return "redirect:/authors";
    }

    @GetMapping("/authors/edit")
    public String editAuthor(@RequestParam("id") long id, Model model) {
        final AuthorDto author = authorService.findById(id);
        model.addAttribute("author", author);
        return "authors/edit";
    }

    @PostMapping("/authors/edit")
    public String editAuthor(@Valid @ModelAttribute("author") AuthorDto author,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "authors/edit";
        }
        authorService.update(author);
        return "redirect:/authors";
    }

    @GetMapping("/authors/delete")
    public String deleteAuthor(@RequestParam("id") long id, Model model) {
        final AuthorDto author = authorService.findById(id);
        model.addAttribute("author", author);
        return "authors/delete";
    }

    @PostMapping("/authors/delete")
    public String deleteAuthor(@RequestParam("id") long id) {
        authorService.deleteById(id);
        return "redirect:/authors";
    }

}
