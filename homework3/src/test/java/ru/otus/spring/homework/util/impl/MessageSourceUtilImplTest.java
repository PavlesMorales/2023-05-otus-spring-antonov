package ru.otus.spring.homework.util.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import ru.otus.spring.homework.TestConfig;
import ru.otus.spring.homework.util.MessageSourceUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageSourceUtilImplTest extends TestConfig {

    @Autowired
    MessageSourceUtil messageSourceUtil;

    @ParameterizedTest
    @CsvSource({"user.hello,Hello",
            "user.first,Input your name...",
            "user.last,Input last name..."})
    void getMessage(String value, String expected) {

        String actual = messageSourceUtil.getMessageWithArgs(value);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"quiz.start, Okay. Now start quiz ivan ivanov, ivan, ivanov",
            "quiz.result.success, Congratulation your scores : 1. minimal scores: 2, 1, 2",
            "quiz.result.failure, Fail. Attempt next time. your scores : 1. minimal scores: 3, 1, 3"})
    void getMessageWithArgs_ShouldReturnMessageWithTwoValues(String placeholder, String expected, String firstValue, String secondValue) {
        String actual = messageSourceUtil.getMessageWithArgs(placeholder, firstValue, secondValue);
        assertEquals(expected, actual);

    }

}