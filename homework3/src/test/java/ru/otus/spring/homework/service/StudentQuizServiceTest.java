package ru.otus.spring.homework.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.homework.TestConfig;
import ru.otus.spring.homework.model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StudentQuizServiceTest extends TestConfig {

    @MockBean
    QuizService quizService;

    @Autowired
    StudentQuizService subj;


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


        StudentQuizResult expected = new StudentQuizResult(
                student,
                studentQuizResult,
                countRightStudentQuestions,
                2);

        //when
        when(quizService.startQuiz(questions)).thenReturn(studentQuizResult);


        StudentQuizResult actual = subj.startStudentQuiz(student, questions);

        //then
        assertEquals(expected, actual);

        verify(quizService, times(1)).startQuiz(questions);

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


        StudentQuizResult expected = new StudentQuizResult(
                student,
                studentQuizResult,
                countRightStudentQuestions,
                2);

        //when
        when(quizService.startQuiz(questions)).thenReturn(studentQuizResult);

        StudentQuizResult actual = subj.startStudentQuiz(student, questions);

        //then
        assertEquals(expected, actual);

        verify(quizService, times(1)).startQuiz(questions);

    }
}