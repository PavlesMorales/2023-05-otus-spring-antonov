package ru.otus.spring.homework.util.impl;

import ru.otus.spring.homework.util.IOConsoleProvider;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IOConsoleProviderImpl implements IOConsoleProvider {

    private final Scanner scanner;

    private final PrintStream outputStream;

    public IOConsoleProviderImpl(InputStream inputStream, PrintStream printStream) {
        this.scanner = new Scanner(inputStream);
        this.outputStream = printStream;

    }

    @Override
    public void println(String message) {
        outputStream.println(message);
    }

    @Override
    public String read() {
        return scanner.nextLine();
    }
}
