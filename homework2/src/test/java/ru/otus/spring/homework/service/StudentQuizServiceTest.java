package ru.otus.spring.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.homework.model.*;
import ru.otus.spring.homework.repository.QuestionRepository;
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
    QuestionRepository repository;
    @Mock
    QuestionsShuffle questionsShuffle;

    StudentQuizService subj;

    int minimalRightAnswers = 2;

    @BeforeEach
    void startUp() {
        subj = new StudentQuizService(ioConsoleUtil, quiz, repository, questionsShuffle, minimalRightAnswers);
    }

    @Test
    void startStudentQuiz_shouldSuccessQuizResult() {
        //given
        String firstName = "Ivan";
        String lastName = "Ivanov";
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

        String printFirstLine = "Hello, input your name...";
        String printSecondLine = "And last name...";
        String printThirdLine = "Okay. Now start quiz %s %s".formatted(firstName, lastName);
        String printFourLine = """
                Question: question one
                answer: rightAnswerOne
                isRight: true
                                
                Question: question two
                answer: rightAnswerTwo
                isRight: true
                """;

        String printFifthLine = "Congratulation your scores : %s. minimal scores: %s"
                .formatted(countRightStudentQuestions, minimalRightAnswers);

        StudentQuizResult expected = new StudentQuizResult(
                new Student(firstName, lastName),
                studentQuizResult,
                countRightStudentQuestions,
                minimalRightAnswers);

        //when
        doNothing().when(ioConsoleUtil).println(printFirstLine);
        when(ioConsoleUtil.read()).thenReturn(firstName, lastName);
        doNothing().when(ioConsoleUtil).println(printSecondLine);
        when(repository.findAllQuestions()).thenReturn(questions);
        when(questionsShuffle.shuffleQuestions(questions)).thenReturn(questions);
        when(quiz.startQuiz(questions)).thenReturn(studentQuizResult);
        doNothing().when(ioConsoleUtil).println(printThirdLine);
        doNothing().when(ioConsoleUtil).println(printFourLine);
        doNothing().when(ioConsoleUtil).println(printFifthLine);

        StudentQuizResult actual = subj.startStudentQuiz();

        //then
        assertEquals(expected, actual);

        verify(ioConsoleUtil, times(1)).println(printFirstLine);
        verify(ioConsoleUtil, times(1)).println(printSecondLine);
        verify(ioConsoleUtil, times(2)).read();
        verify(repository, times(1)).findAllQuestions();
        verify(questionsShuffle, times(1)).shuffleQuestions(questions);
        verify(quiz, times(1)).startQuiz(questions);
        verify(ioConsoleUtil, times(1)).println(printThirdLine);
        verify(ioConsoleUtil, times(1)).println(printFourLine);
        verify(ioConsoleUtil, times(1)).println(printFifthLine);

    }

    @Test
    void startStudentQuiz_shouldFailQuizResult() {
        //given
        String firstName = "Ivan";
        String lastName = "Ivanov";
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

        String printFirstLine = "Hello, input your name...";
        String printSecondLine = "And last name...";
        String printThirdLine = "Okay. Now start quiz %s %s".formatted(firstName, lastName);
        String printFourLine = """
                Question: question one
                answer: rightAnswerOne
                isRight: true
                                
                Question: question two
                answer: wrongAnswerOne
                isRight: false
                """;

        String printFifthLine = "Fail. Attempt next time. your scores : %s. minimal scores: %s"
                .formatted(countRightStudentQuestions, minimalRightAnswers);

        StudentQuizResult expected = new StudentQuizResult(
                new Student(firstName, lastName),
                studentQuizResult,
                countRightStudentQuestions,
                minimalRightAnswers);

        //when
        doNothing().when(ioConsoleUtil).println(printFirstLine);
        when(ioConsoleUtil.read()).thenReturn(firstName, lastName);
        doNothing().when(ioConsoleUtil).println(printSecondLine);
        when(repository.findAllQuestions()).thenReturn(questions);
        when(questionsShuffle.shuffleQuestions(questions)).thenReturn(questions);
        when(quiz.startQuiz(questions)).thenReturn(studentQuizResult);
        doNothing().when(ioConsoleUtil).println(printThirdLine);
        doNothing().when(ioConsoleUtil).println(printFourLine);
        doNothing().when(ioConsoleUtil).println(printFifthLine);

        StudentQuizResult actual = subj.startStudentQuiz();

        //then
        assertEquals(expected, actual);

        verify(ioConsoleUtil, times(1)).println(printFirstLine);
        verify(ioConsoleUtil, times(1)).println(printSecondLine);
        verify(ioConsoleUtil, times(2)).read();
        verify(repository, times(1)).findAllQuestions();
        verify(questionsShuffle, times(1)).shuffleQuestions(questions);
        verify(quiz, times(1)).startQuiz(questions);
        verify(ioConsoleUtil, times(1)).println(printThirdLine);
        verify(ioConsoleUtil, times(1)).println(printFourLine);
        verify(ioConsoleUtil, times(1)).println(printFifthLine);

    }
}