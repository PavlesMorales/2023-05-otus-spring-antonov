package ru.otus.spring.homework.util.impl;

import ru.otus.spring.homework.util.Printable;

public class ConsolePrinter implements Printable {

    @Override
    public void println(String message) {
        System.out.println(message);
    }
}
