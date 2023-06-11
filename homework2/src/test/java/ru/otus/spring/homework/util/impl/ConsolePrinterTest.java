package ru.otus.spring.homework.util.impl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsolePrinterTest {

    @Mock
    Scanner scanner;

    @InjectMocks
    IOConsoleUtilImpl subj;

    ByteArrayOutputStream stream;


    @BeforeEach
    void init() {
        stream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(stream));

    }

    @Test
    void test_shouldPrintMessage() throws IOException {
        String expected = "Hello" + System.lineSeparator();
        String message = "Hello";

        subj.println(message);

        stream.flush();
        String actual = stream.toString();

        assertEquals(expected, actual);

        verifyNoInteractions(scanner);

    }

    @Test
    void read_shouldReadFromConsole() {
        String expected = "Hello world";
        when(scanner.nextLine()).thenReturn(expected);
        String actual = subj.read();

        assertEquals(expected, actual);
        verify(scanner, times(1)).nextLine();
    }

    @AfterAll
    static void shoutDown() {
        System.setOut(System.out);
    }
}