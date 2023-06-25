package ru.otus.spring.homework.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.spring.homework.TestConfig;

import static org.junit.jupiter.api.Assertions.*;

class AppPropertiesTest extends TestConfig {

    @Autowired
    AppProperties subj;

    @Test
    void getLocale() {
        assertNotNull(subj.getLocale());
    }

    @Test
    void getMinimalRightCountAnswers() {
        assertNotEquals(0, subj.getMinimalRightCountAnswers());
    }

    @Test
    void getResourceName() {
        assertNotNull(subj.getResourceName());
    }
}