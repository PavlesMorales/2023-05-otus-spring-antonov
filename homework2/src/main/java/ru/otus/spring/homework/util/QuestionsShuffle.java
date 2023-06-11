package ru.otus.spring.homework.util;

import ru.otus.spring.homework.model.Question;

import java.util.List;

public interface QuestionsShuffle {

    List<Question> shuffleQuestions(List<Question> questions);

}
