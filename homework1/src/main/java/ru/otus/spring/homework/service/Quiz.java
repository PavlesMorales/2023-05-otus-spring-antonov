package ru.otus.spring.homework.service;

import ru.otus.spring.homework.model.Answer;
import ru.otus.spring.homework.model.Question;
import ru.otus.spring.homework.repository.QuestionRepository;
import ru.otus.spring.homework.util.Printable;

import java.util.List;
import java.util.stream.Collectors;

public class Quiz {

    private static final String QUIZ_TEMPLATE = "Question %s : ";

    private final Printable printable;

    private final QuestionRepository repository;

    public Quiz(Printable printable, QuestionRepository repository) {
        this.printable = printable;
        this.repository = repository;
    }

    public void startQuiz() {
        printable.println("Start quiz!!");
        List<Question> currentQuestions = repository.findAllQuestions();

        for (int i = 0; i < currentQuestions.size(); i++) {
            printable.println(QUIZ_TEMPLATE.formatted(i + 1));

            var question = currentQuestions.get(i);
            printable.println(question.question());

            String answersAsString = getAnswersAsString(question.answers());

            printable.println(answersAsString);

            printable.println("");
        }

        printable.println("Stop quiz");
    }

    private String getAnswersAsString(List<Answer> answers) {

        return answers.stream()
                .map(Answer::answer)
                .filter(answer -> !answer.isBlank())
                .collect(Collectors.joining(System.lineSeparator()));
    }

}
