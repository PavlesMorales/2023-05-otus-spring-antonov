package ru.otus.spring.homework.util.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.spring.homework.TestConfig;
import ru.otus.spring.homework.util.MessageSourceProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageSourceProviderImplTest extends TestConfig {

    @Autowired
    MessageSourceProvider messageSourceProvider;

    @ParameterizedTest
    @CsvSource("quiz.reason, Before login")
    void getMessage(String value, String expected) {

        String actual = messageSourceProvider.getMessage(value);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"quiz.start, Okay. Now start quiz ivan ivanov, ivan, ivanov",
            "quiz.result.success, Congratulation your scores : 1. minimal scores: 2, 1, 2",
            "quiz.result.failure, Fail. Attempt next time. your scores : 1. minimal scores: 3, 1, 3"})
    void getMessageWithArgs_ShouldReturnMessageWithTwoValues(String placeholder, String expected, String firstValue, String secondValue) {
        String actual = messageSourceProvider.getMessageWithArgs(placeholder, firstValue, secondValue);
        assertEquals(expected, actual);

    }

}