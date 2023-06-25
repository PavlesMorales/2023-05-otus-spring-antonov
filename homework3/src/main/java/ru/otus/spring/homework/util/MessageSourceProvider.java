package ru.otus.spring.homework.util;

public interface MessageSourceProvider {

    String getMessage(String key);

    String getMessageWithArgs(String key, Object... args);
}
