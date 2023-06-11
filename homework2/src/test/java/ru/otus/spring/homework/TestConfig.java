package ru.otus.spring.homework;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestPropertySource("classpath:application.yaml")
@ContextConfiguration(classes = Main.class)
@ExtendWith(SpringExtension.class)
public abstract class TestConfig {
}
