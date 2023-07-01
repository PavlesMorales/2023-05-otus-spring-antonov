package ru.otus.spring.homework.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.shell.InputProvider;
import org.springframework.shell.ResultHandlerService;
import org.springframework.shell.Shell;
import ru.otus.spring.homework.TestConfig;
import ru.otus.spring.homework.model.*;
import ru.otus.spring.homework.repository.QuestionRepository;
import ru.otus.spring.homework.repository.StudentRepository;
import ru.otus.spring.homework.service.StudentQuizService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class StudentQuizViewTest extends TestConfig {


    InputProvider inputProvider;

    ArgumentCaptor<Object> argumentCaptor;

    @SpyBean
    ResultHandlerService resultHandlerService;

    @Autowired
    Shell shell;

    @MockBean
    StudentQuizService studentQuizService;

    @MockBean
    StudentRepository studentRepository;

    @MockBean
    QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        inputProvider = mock(InputProvider.class);
        argumentCaptor = ArgumentCaptor.forClass(Object.class);
    }

    @Test
    void shouldSuccessLogin() throws Exception {

        Student student = new Student("Ivan", "Ivanov");


        doNothing().when(studentRepository).save(student);

        when(inputProvider.readInput())
                .thenReturn(() -> "l Ivan Ivanov")
                .thenReturn(null);


        shell.run(inputProvider);
        verify(resultHandlerService, times(1)).handle(argumentCaptor.capture());

        List<Object> results = argumentCaptor.getAllValues();

        assertThat(results).contains("Hello Ivan Ivanov");

        verify(studentRepository, times(1)).save(student);

    }

    @Test
    void shouldSuccessStartQuiz() throws Exception {

        Student student = new Student("Ivan", "Ivanov");
        List<Question> questions = List.of(
                new Question("question one", List.of(
                        new Answer(false, "wrongAnswerOne"),
                        new Answer(true, "rightAnswerTwo")
                )),
                new Question("question two", List.of(
                        new Answer(true, "rightAnswerTwo"),
                        new Answer(false, "wrongAnswerOne")
                )));

        List<QuizResult> quizResults = List.of(
                new QuizResult("question one", new Answer(true, "rightAnswerOne")),
                new QuizResult("question two", new Answer(true, "rightAnswerTwo")));

        StudentQuizResult studentQuizResult = new StudentQuizResult(
                student,
                quizResults,
                2,
                2);

        given(studentRepository.getStudent()).willReturn(student);
        given(questionRepository.findAllQuestionsInRandomOrder()).willReturn(questions);
        given(studentQuizService.startStudentQuiz(student, questions)).willReturn(studentQuizResult);

        when(inputProvider.readInput())
                .thenReturn(() -> "start")
                .thenReturn(null);


        shell.run(inputProvider);
        verify(resultHandlerService, times(1)).handle(argumentCaptor.capture());

        List<Object> results = argumentCaptor.getAllValues();

        assertThat(results).contains("Goodbye: Ivan Ivanov");


        verify(studentRepository, times(2)).getStudent();
        verify(questionRepository, times(1)).findAllQuestionsInRandomOrder();
        verify(studentQuizService, times(1)).startStudentQuiz(student, questions);

    }
}