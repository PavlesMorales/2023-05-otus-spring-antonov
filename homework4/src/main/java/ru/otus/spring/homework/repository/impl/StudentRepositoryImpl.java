package ru.otus.spring.homework.repository.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.homework.model.Student;
import ru.otus.spring.homework.repository.StudentRepository;
import ru.otus.spring.homework.util.IOConsoleProvider;
import ru.otus.spring.homework.util.MessageSourceProvider;

@Component
public class StudentRepositoryImpl implements StudentRepository {

    private final IOConsoleProvider ioConsoleProvider;

    private final MessageSourceProvider messageSource;

    public StudentRepositoryImpl(IOConsoleProvider ioConsoleProvider, MessageSourceProvider messageSource) {
        this.ioConsoleProvider = ioConsoleProvider;
        this.messageSource = messageSource;
    }

    @Override
    public Student getStudent() {
        ioConsoleProvider.println(messageSource.getMessage("user.hello"));
        ioConsoleProvider.println(messageSource.getMessage("user.first"));
        String firstName = ioConsoleProvider.read();
        ioConsoleProvider.println(messageSource.getMessage("user.last"));
        String lastName = ioConsoleProvider.read();
        return new Student(firstName, lastName);
    }
}
