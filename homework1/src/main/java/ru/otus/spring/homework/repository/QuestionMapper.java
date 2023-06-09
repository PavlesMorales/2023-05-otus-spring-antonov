package ru.otus.spring.homework.repository;

import ru.otus.spring.homework.model.Question;

public interface QuestionMapper {

    Question readValue(String string);

}
