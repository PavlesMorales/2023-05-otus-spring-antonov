package ru.otus.spring.homework.repository;

import ru.otus.spring.homework.model.Student;

public interface StudentRepository {

    Student getStudent();

    void save(Student student);
}
