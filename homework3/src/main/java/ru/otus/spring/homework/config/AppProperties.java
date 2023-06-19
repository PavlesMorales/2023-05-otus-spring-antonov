package ru.otus.spring.homework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
public record AppProperties(
        int minimalCountRightAnswers,
        String pathToQuestions,
        Locale locale) {
}
