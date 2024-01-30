package ru.otus.spring.homework.domain.author;

import lombok.Builder;

@Builder(toBuilder = true)
public record Author(Long id, String firstName, String lastName) {
}
