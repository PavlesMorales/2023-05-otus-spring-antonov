package ru.otus.spring.homework.util.impl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConsolePrinterTest {

    @InjectMocks
    ConsolePrinter subj;

    ByteArrayOutputStream stream;

    @BeforeEach
    void init() {
        stream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stream));
    }

    @Test
    void test_shouldPrintMessage() throws IOException {
        String expected = "Hello\n";
        String message = "Hello";

        subj.print(message);

        stream.flush();
        String actual = stream.toString();

        assertEquals(expected, actual);

    }

    @AfterAll
    static void shoutDown() {
        System.setOut(System.out);
    }
}