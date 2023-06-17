package ru.otus.spring.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.homework.model.*;
import ru.otus.spring.homework.repository.QuestionRepository;
import ru.otus.spring.homework.repository.StudentRepository;
import ru.otus.spring.homework.util.IOConsoleUtil;
import ru.otus.spring.homework.util.QuestionsShuffle;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentQuizServiceTest {

    @Mock
    IOConsoleUtil ioConsoleUtil;

    @Mock
    Quiz quiz;

    @Mock
    StudentRepository studentRepository;

    @Mock
    QuestionRepository repository;

    StudentQuizService subj;

    int minimalRightAnswers = 2;

    @BeforeEach
    void startUp() {
        subj = new StudentQuizService(ioConsoleUtil, quiz, studentRepository, repository, minimalRightAnswers);
    }

    @Test
    void startStudentQuiz_shouldSuccessQuizResult() {
        //given
        var student = new Student("Ivan", "Ivanov");
        int countRightStudentQuestions = 2;

        List<Question> questions = List.of(
                new Question("question one", List.of(
                        new Answer(false, "wrongAnswerOne"),
                        new Answer(true, "rightAnswerTwo")
                )),
                new Question("question two", List.of(
                        new Answer(true, "rightAnswerTwo"),
                        new Answer(false, "wrongAnswerOne")
                )));


        List<QuizResult> studentQuizResult = List.of(
                new QuizResult("question one", new Answer(true, "rightAnswerOne")),
                new QuizResult("question two", new Answer(true, "rightAnswerTwo")));

        String printFirstLine = "Okay. Now start quiz %s %s".formatted(student.firstName(), student.lastName());
        String printSecondLine = """
                Question: question one
                answer: rightAnswerOne
                isRight: true
                                
                Question: question two
                answer: rightAnswerTwo
                isRight: true
                """;

        String printThirdLine = "Congratulation your scores : %s. minimal scores: %s"
                .formatted(countRightStudentQuestions, minimalRightAnswers);

        StudentQuizResult expected = new StudentQuizResult(
                student,
                studentQuizResult,
                countRightStudentQuestions,
                minimalRightAnswers);

        //when
        when(studentRepository.getStudent()).thenReturn(student);
        doNothing().when(ioConsoleUtil).println(printFirstLine);
        doNothing().when(ioConsoleUtil).println(printSecondLine);
        when(repository.findAllQuestionsInRandomOrder()).thenReturn(questions);
        when(quiz.startQuiz(questions)).thenReturn(studentQuizResult);
        doNothing().when(ioConsoleUtil).println(printThirdLine);

        StudentQuizResult actual = subj.startStudentQuiz();

        //then
        assertEquals(expected, actual);

        verify(studentRepository, times(1)).getStudent();
        verify(ioConsoleUtil, times(1)).println(printFirstLine);
        verify(ioConsoleUtil, times(1)).println(printSecondLine);
        verify(repository, times(1)).findAllQuestionsInRandomOrder();
        verify(quiz, times(1)).startQuiz(questions);
        verify(ioConsoleUtil, times(1)).println(printThirdLine);

    }

    @Test
    void startStudentQuiz_shouldFailQuizResult() {
        //given
        var student = new Student("Ivan", "Ivanov");
        int countRightStudentQuestions = 1;

        List<Question> questions = List.of(
                new Question("question one", List.of(
                        new Answer(false, "wrongAnswerOne"),
                        new Answer(true, "rightAnswerTwo")
                )),
                new Question("question two", List.of(
                        new Answer(true, "rightAnswerTwo"),
                        new Answer(false, "wrongAnswerOne")
                )));


        List<QuizResult> studentQuizResult = List.of(
                new QuizResult("question one", new Answer(true, "rightAnswerOne")),
                new QuizResult("question two", new Answer(false, "wrongAnswerOne")));

        String printFirstLine = "Okay. Now start quiz %s %s".formatted(student.firstName(), student.lastName());
        String printSecondLine = """
                Question: question one
                answer: rightAnswerOne
                isRight: true
                                
                Question: question two
                answer: wrongAnswerOne
                isRight: false
                """;

        String printThirdLine = "Fail. Attempt next time. your scores : %s. minimal scores: %s"
                .formatted(countRightStudentQuestions, minimalRightAnswers);

        StudentQuizResult expected = new StudentQuizResult(
                student,
                studentQuizResult,
                countRightStudentQuestions,
                minimalRightAnswers);

        //when
        when(studentRepository.getStudent()).thenReturn(student);
        doNothing().when(ioConsoleUtil).println(printFirstLine);
        doNothing().when(ioConsoleUtil).println(printSecondLine);
        when(repository.findAllQuestionsInRandomOrder()).thenReturn(questions);
        when(quiz.startQuiz(questions)).thenReturn(studentQuizResult);
        doNothing().when(ioConsoleUtil).println(printThirdLine);

        StudentQuizResult actual = subj.startStudentQuiz();

        //then
        assertEquals(expected, actual);

        verify(ioConsoleUtil, times(1)).println(printFirstLine);
        verify(ioConsoleUtil, times(1)).println(printSecondLine);
        verify(repository, times(1)).findAllQuestionsInRandomOrder();
        verify(quiz, times(1)).startQuiz(questions);
        verify(ioConsoleUtil, times(1)).println(printThirdLine);

    }
}