package ru.otus.spring.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.model.Answer;
import ru.otus.spring.homework.model.Question;
import ru.otus.spring.homework.model.QuizResult;
import ru.otus.spring.homework.model.StudentQuizResult;
import ru.otus.spring.homework.repository.QuestionRepository;
import ru.otus.spring.homework.repository.StudentRepository;
import ru.otus.spring.homework.util.IOConsoleUtil;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentQuizService {

    private final IOConsoleUtil ioConsoleUtil;

    private final Quiz quiz;

    private final StudentRepository studentRepository;

    private final QuestionRepository repository;


    private final int minimalRightAnswers;

    public StudentQuizService(IOConsoleUtil ioConsoleUtil,
                              Quiz quiz,
                              StudentRepository studentRepository,
                              QuestionRepository repository,
                              @Value("${minimal-count-right-answers}") int minimalRightAnswers) {

        this.ioConsoleUtil = ioConsoleUtil;
        this.quiz = quiz;
        this.studentRepository = studentRepository;
        this.repository = repository;
        this.minimalRightAnswers = minimalRightAnswers;
    }

    public StudentQuizResult startStudentQuiz() {
        var student = studentRepository.getStudent();

        ioConsoleUtil.println("Okay. Now start quiz %s %s".formatted(student.firstName(), student.lastName()));


        List<Question> questions = repository.findAllQuestionsInRandomOrder();

        List<QuizResult> studentQuizResult = quiz.startQuiz(questions);
        ioConsoleUtil.println(getQuestionWithStudentAnswers(studentQuizResult));

        int countRightStudentQuestions = getRightCountAnswers(studentQuizResult);

        if (countRightStudentQuestions >= minimalRightAnswers) {
            ioConsoleUtil.println("Congratulation your scores : %s. minimal scores: %s"
                    .formatted(countRightStudentQuestions, minimalRightAnswers));
        } else {
            ioConsoleUtil.println("Fail. Attempt next time. your scores : %s. minimal scores: %s"
                    .formatted(countRightStudentQuestions, minimalRightAnswers));
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
                .map(question -> "Question: %s%s%s"
                        .formatted(question.questionText(), System.lineSeparator(), question.answer()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

}
