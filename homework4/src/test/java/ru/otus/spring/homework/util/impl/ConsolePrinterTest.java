package ru.otus.spring.homework.util.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsolePrinterTest {

    @Mock
    InputStream inputStream;

    @Mock
    PrintStream printStream;

    @InjectMocks
    IOConsoleProviderImpl subj;


    @Test
    void test_shouldPrintMessage() {
        String message = "Hello";

        doNothing().when(printStream).println(message);
        subj.println(message);

        verify(printStream, times(1)).println(message);
    }

}