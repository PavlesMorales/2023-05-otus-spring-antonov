package ru.otus.spring.homework.util.impl;

import ru.otus.spring.homework.util.IOConsoleUtil;

import java.util.Scanner;

public class IOConsoleUtilImpl implements IOConsoleUtil {

    private final Scanner scanner;

    public IOConsoleUtilImpl(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void println(String message) {
        System.out.println(message);
    }

    @Override
    public String read() {
        return scanner.nextLine();
    }
}
