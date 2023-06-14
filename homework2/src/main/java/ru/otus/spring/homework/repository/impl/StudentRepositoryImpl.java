package ru.otus.spring.homework.repository.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.homework.model.Student;
import ru.otus.spring.homework.repository.StudentRepository;
import ru.otus.spring.homework.util.IOConsoleUtil;

@Component
public class StudentRepositoryImpl implements StudentRepository {

    private final IOConsoleUtil ioConsoleUtil;

    public StudentRepositoryImpl(IOConsoleUtil ioConsoleUtil) {
        this.ioConsoleUtil = ioConsoleUtil;
    }

    @Override
    public Student getStudent() {
        ioConsoleUtil.println("Hello, input your name...");
        String firstName = ioConsoleUtil.read();
        ioConsoleUtil.println("And last name...");
        String lastName = ioConsoleUtil.read();
        return new Student(firstName, lastName);
    }
}
