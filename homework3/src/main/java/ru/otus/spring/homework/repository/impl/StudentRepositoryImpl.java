package ru.otus.spring.homework.repository.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.homework.model.Student;
import ru.otus.spring.homework.repository.StudentRepository;
import ru.otus.spring.homework.util.IOConsoleUtil;
import ru.otus.spring.homework.util.MessageSourceUtil;

@Component
public class StudentRepositoryImpl implements StudentRepository {

    private final IOConsoleUtil ioConsoleUtil;

    private final MessageSourceUtil messageSource;

    public StudentRepositoryImpl(IOConsoleUtil ioConsoleUtil, MessageSourceUtil messageSource) {
        this.ioConsoleUtil = ioConsoleUtil;
        this.messageSource = messageSource;
    }

    @Override
    public Student getStudent() {
        ioConsoleUtil.println(messageSource.getMessage("user.hello"));
        ioConsoleUtil.println(messageSource.getMessage("user.first"));
        String firstName = ioConsoleUtil.read();
        ioConsoleUtil.println(messageSource.getMessage("user.last"));
        String lastName = ioConsoleUtil.read();
        return new Student(firstName, lastName);
    }
}
