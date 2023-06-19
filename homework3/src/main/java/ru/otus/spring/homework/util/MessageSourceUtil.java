package ru.otus.spring.homework.util;

public interface MessageSourceUtil {

    String getMessage(String key);

    String getMessageWithArgs(String key, Object... args);
}
