package ru.otus.spring.homework.repository.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.homework.model.Student;
import ru.otus.spring.homework.repository.StudentRepository;

@Component
public class StudentInMemoryRepository implements StudentRepository {

    private Student student;

    @Override
    public Student getStudent() {
        return student;
    }

    @Override
    public void save(Student student) {
        this.student = student;
    }
}
