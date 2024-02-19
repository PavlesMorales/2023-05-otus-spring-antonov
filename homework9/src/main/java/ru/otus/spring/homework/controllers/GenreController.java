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
import ru.otus.spring.homework.models.dto.GenreDto;
import ru.otus.spring.homework.services.GenreService;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;


    @GetMapping("/genres")
    public String findAllGenres(Model model) {
        model.addAttribute("genres", genreService.findAll());
        return "genres/list";
    }

    @GetMapping("/genres/create")
    public String createGenre(Model model) {
        model.addAttribute("genre", GenreDto.builder().build());
        return "genres/create";
    }

    @PostMapping("/genres/create")
    public String createGenre(@Valid @ModelAttribute("genre") GenreDto genreDto,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "genres/create";
        }
        genreService.create(genreDto);
        return "redirect:/genres";
    }

    @GetMapping("/genres/delete")
    public String deleteGenre(@RequestParam("id") long id, Model model) {
        final GenreDto genre = genreService.findById(id);
        model.addAttribute("genre", genre);
        return "genres/delete";
    }

    @PostMapping("/genres/delete")
    public String deleteGenre(@RequestParam("id") long id) {
        genreService.deleteById(id);
        return "redirect:/genres";
    }

    @GetMapping("/genres/edit")
    public String editGenre(@RequestParam("id") long id, Model model) {
        final GenreDto genre = genreService.findById(id);
        model.addAttribute("genre", genre);
        return "genres/edit";
    }

    @PostMapping("/genres/edit")
    public String editGenre(@Valid @ModelAttribute("genre") GenreDto genreDto,
                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "genres/edit";
        }
        genreService.update(genreDto);
        return "redirect:/genres";
    }
}
