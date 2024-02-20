package ru.otus.spring.homework.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;

    @NotBlank(message = "{title-field-should-not-be-blank}")
    @Size(min = 2, max = 255, message = "{title-field-should-has-expected-size}")
    private String title;

    private AuthorDto authorDto;

    private GenreDto genreDto;
}
