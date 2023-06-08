package ru.otus.spring.homework.util;

import ru.otus.spring.homework.model.Question;

public interface QuestionMapper {

    Question readValue(String string);

}
