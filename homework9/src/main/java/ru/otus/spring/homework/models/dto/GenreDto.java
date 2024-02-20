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
public class GenreDto {

    private Long id;

    @NotBlank(message = "{genre-name-field-should-not-be-blank}")
    @Size(min = 2, max = 20, message = "{genre-name-field-should-has-expected-size}")
    private String name;
}
