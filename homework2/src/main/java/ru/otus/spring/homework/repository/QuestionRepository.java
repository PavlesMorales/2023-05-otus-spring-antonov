package ru.otus.spring.homework.repository;

import ru.otus.spring.homework.model.Question;

import java.util.List;

public interface QuestionRepository {

    List<Question> findAllQuestions();
}
