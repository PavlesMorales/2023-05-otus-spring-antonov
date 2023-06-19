package ru.otus.spring.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework.config.AppProperties;
import ru.otus.spring.homework.model.Answer;
import ru.otus.spring.homework.model.Question;
import ru.otus.spring.homework.model.QuizResult;
import ru.otus.spring.homework.model.StudentQuizResult;
import ru.otus.spring.homework.model.Student;
import ru.otus.spring.homework.util.IOConsoleUtil;
import ru.otus.spring.homework.util.MessageSourceUtil;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentQuizService {

    private final MessageSourceUtil messageSourceUtil;

    private final IOConsoleUtil ioConsoleUtil;

    private final QuizService quiz;


    private final int minimalRightAnswers;

    public StudentQuizService(MessageSourceUtil messageSourceUtil,
                              IOConsoleUtil ioConsoleUtil,
                              QuizService quiz,
                              AppProperties properties) {

        this.messageSourceUtil = messageSourceUtil;
        this.ioConsoleUtil = ioConsoleUtil;
        this.quiz = quiz;
        this.minimalRightAnswers = properties.minimalCountRightAnswers();
    }

    public StudentQuizResult startStudentQuiz(Student student, List<Question> allQuestionsInRandomOrder) {

        ioConsoleUtil.println(messageSourceUtil
                .getMessageWithArgs("quiz.start", student.firstName(), student.lastName()));


        List<QuizResult> studentQuizResult = quiz.startQuiz(allQuestionsInRandomOrder);
        ioConsoleUtil.println(getQuestionWithStudentAnswers(studentQuizResult));

        int countRightStudentQuestions = getRightCountAnswers(studentQuizResult);

        if (countRightStudentQuestions >= minimalRightAnswers) {
            ioConsoleUtil.println(messageSourceUtil
                    .getMessageWithArgs("quiz.result.success", countRightStudentQuestions, minimalRightAnswers));
        } else {
            ioConsoleUtil.println(messageSourceUtil
                    .getMessageWithArgs("quiz.result.failure", countRightStudentQuestions, minimalRightAnswers));
        }
        return new StudentQuizResult(student, studentQuizResult, countRightStudentQuestions, minimalRightAnswers);
    }

    private int getRightCountAnswers(List<QuizResult> studentQuestions) {

        return Math.toIntExact(studentQuestions.stream()
                .map(QuizResult::answer)
                .filter(Answer::isRight)
                .count());
    }

    private String getQuestionWithStudentAnswers(List<QuizResult> questions) {

        return questions.stream()
                .map(question -> messageSourceUtil.getMessageWithArgs(
                        "quiz.question.with.answer",
                        question.questionText(),
                        System.lineSeparator(),
                        question.answer()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

}
