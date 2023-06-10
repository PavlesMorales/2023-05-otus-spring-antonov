package ru.otus.spring.homework.util.impl;

import org.junit.jupiter.api.Test;
import ru.otus.spring.homework.exceptions.ResourceReaderException;
import ru.otus.spring.homework.repository.impl.ResourceCsvReader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResourceCsvReaderTest {

    ResourceCsvReader subj;

    @Test
    void readResourceAsStrings() {
        String content = """
                What is a correct syntax to output "Hello World" in Java?, System.out.println("Hello World");, Console.WriteLine("Hello World");, print("Hello World");, echo("Hello World");""";

        List<String> expected = List.of(content);
        subj = new ResourceCsvReader("/test-questions.csv");
        List<String> actual = subj.readResourceAsStrings();
        assertEquals(expected, actual);

    }

    @Test
    void readResourceAsStrings_shouldThrownExceptionBecauseResourceIsNull() {

        subj = new ResourceCsvReader("/some-other-path.csv");

        assertThrows(ResourceReaderException.class, () -> subj.readResourceAsStrings());

    }
}