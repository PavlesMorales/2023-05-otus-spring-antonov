package ru.otus.spring.homework.util.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.homework.model.Answer;
import ru.otus.spring.homework.model.Question;
import ru.otus.spring.homework.util.QuestionsShuffle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class QuestionsShuffleImpl implements QuestionsShuffle {

    @Override
    public List<Question> shuffleQuestions(List<Question> questions) {
        Objects.requireNonNull(questions, "Questions must not be null");

        if (questions.isEmpty()) {
            return questions;
        }

        List<Question> shuffledQuestions = new ArrayList<>();
        for (Question question : questions) {
            List<Answer> answers = new ArrayList<>(question.answers());
            Collections.shuffle(answers);
            shuffledQuestions.add(new Question(question.question(), answers));
        }

        Collections.shuffle(shuffledQuestions);
        return shuffledQuestions;
    }
}
