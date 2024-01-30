package ru.otus.spring.homework.domain.genre;

import lombok.Builder;

@Builder(toBuilder = true)
public record Genre(Long id, String genreName) {
}
