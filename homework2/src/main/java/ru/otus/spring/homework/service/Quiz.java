package ru.otus.spring.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework.model.Answer;
import ru.otus.spring.homework.model.Question;
import ru.otus.spring.homework.model.QuizResult;
import ru.otus.spring.homework.util.IOConsoleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Quiz {

    private static final String QUIZ_TEMPLATE = "Question %s : ";

    private final IOConsoleUtil ioConsoleUtil;

    public Quiz(IOConsoleUtil ioConsoleUtil) {
        this.ioConsoleUtil = ioConsoleUtil;
    }

    public List<QuizResult> startQuiz(List<Question> questions) {
        List<QuizResult> questionWithStudentAnswer = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {
            ioConsoleUtil.println(QUIZ_TEMPLATE.formatted(i + 1));
            var question = questions.get(i);
            ioConsoleUtil.println(question.question());

            String answersForMultipleChoice = getAnswersAsString(question.answers());
            ioConsoleUtil.println(answersForMultipleChoice);

            String studentAnswerFromConsole = ioConsoleUtil.read();
            Answer studentAnswer = findStudentAnswer(question.answers(), studentAnswerFromConsole);

            questionWithStudentAnswer.add(new QuizResult(question.question(), studentAnswer));
        }

        return questionWithStudentAnswer;
    }

    private Answer findStudentAnswer(List<Answer> currentAnswers, String studentAnswer) {

        return currentAnswers.stream()
                .filter(answer -> answer.answer().equals(studentAnswer))
                .findFirst()
                .orElse(new Answer(false, studentAnswer));
    }

    private String getAnswersAsString(List<Answer> answers) {

        return answers.stream()
                .map(Answer::answer)
                .filter(answer -> !answer.isBlank())
                .collect(Collectors.joining(System.lineSeparator()));
    }

}
