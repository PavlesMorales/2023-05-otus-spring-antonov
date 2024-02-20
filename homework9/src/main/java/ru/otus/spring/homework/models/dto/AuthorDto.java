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
public class AuthorDto {

    private Long id;

    @NotBlank(message = "{first-name-field-should-not-be-blank}")
    @Size(min = 2, max = 40, message = "{first-name-field-should-has-expected-size}")
    private String firstName;

    @NotBlank(message = "{last-name-field-should-not-be-blank}")
    @Size(min = 2, max = 40, message = "{last-name-field-should-has-expected-size}")
    private String lastName;
}

