package ru.otus.spring.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.homework.model.Answer;
import ru.otus.spring.homework.model.Question;
import ru.otus.spring.homework.model.QuizResult;
import ru.otus.spring.homework.model.Student;
import ru.otus.spring.homework.model.StudentQuizResult;
import ru.otus.spring.homework.util.IOService;
import ru.otus.spring.homework.util.MessageSourceProvider;
import ru.otus.spring.homework.util.MinimalCountRightAnswersProvider;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentQuizService {

    private final MessageSourceProvider messageSourceProvider;

    private final IOService ioConsoleUtil;

    private final QuizService quiz;

    private final MinimalCountRightAnswersProvider minimalRightAnswersProvider;

    public StudentQuizService(MessageSourceProvider messageSourceProvider,
                              IOService ioConsoleUtil,
                              QuizService quiz,
                              MinimalCountRightAnswersProvider minimalRightAnswersProvider) {

        this.messageSourceProvider = messageSourceProvider;
        this.ioConsoleUtil = ioConsoleUtil;
        this.quiz = quiz;
        this.minimalRightAnswersProvider = minimalRightAnswersProvider;
    }

    public StudentQuizResult startStudentQuiz(Student student, List<Question> allQuestionsInRandomOrder) {

        ioConsoleUtil.println(messageSourceProvider
                .getMessageWithArgs("quiz.start", student.firstName(), student.lastName()));


        List<QuizResult> studentQuizResult = quiz.startQuiz(allQuestionsInRandomOrder);
        ioConsoleUtil.println(getQuestionWithStudentAnswers(studentQuizResult));

        int countRightStudentQuestions = getRightCountAnswers(studentQuizResult);
        int minimalRightCountAnswers = minimalRightAnswersProvider.getMinimalRightCountAnswers();
        if (countRightStudentQuestions >= minimalRightCountAnswers) {
            ioConsoleUtil.println(messageSourceProvider
                    .getMessageWithArgs("quiz.result.success", countRightStudentQuestions, minimalRightCountAnswers));
        } else {
            ioConsoleUtil.println(messageSourceProvider
                    .getMessageWithArgs("quiz.result.failure", countRightStudentQuestions, minimalRightCountAnswers));
        }
        return new StudentQuizResult(student, studentQuizResult, countRightStudentQuestions, minimalRightCountAnswers);
    }

    private int getRightCountAnswers(List<QuizResult> studentQuestions) {

        return Math.toIntExact(studentQuestions.stream()
                .map(QuizResult::answer)
                .filter(Answer::isRight)
                .count());
    }

    private String getQuestionWithStudentAnswers(List<QuizResult> questions) {

        return questions.stream()
                .map(question -> messageSourceProvider.getMessageWithArgs(
                        "quiz.question.with.answer",
                        question.questionText(),
                        System.lineSeparator(),
                        question.answer()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

}
