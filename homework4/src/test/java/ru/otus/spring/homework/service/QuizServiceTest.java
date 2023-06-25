package ru.otus.spring.homework.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.homework.TestConfig;
import ru.otus.spring.homework.model.Answer;
import ru.otus.spring.homework.model.Question;
import ru.otus.spring.homework.model.QuizResult;
import ru.otus.spring.homework.util.IOConsoleProvider;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class QuizServiceTest extends TestConfig {

    @MockBean
    IOConsoleProvider ioConsoleUtil;

    @Autowired
    QuizService subj;

    @Test
    void startQuiz() {
        //given
        List<QuizResult> expected = List.of(
                new QuizResult("question one", new Answer(true, "rightAnswerOne")),
                new QuizResult("question two", new Answer(true, "rightAnswerTwo")));

        List<Question> questions = List.of(
                new Question("question one", List.of(
                        new Answer(false, "wrongAnswerOne"),
                        new Answer(true, "rightAnswerOne")
                )),
                new Question("question two", List.of(
                        new Answer(true, "rightAnswerTwo"),
                        new Answer(false, "wrongAnswerTwo")
                )));

        //when
        String questionOne = "Question 1 :";
        String questionTextOne = "question one";
        String answersTextOne = """
                wrongAnswerOne
                rightAnswerOne""";
        String questionTwo = "Question 2 :";
        String questionTextTwo = "question two";
        String answersTextTwo = """
                rightAnswerTwo
                wrongAnswerTwo""";

        String answerOne = "rightAnswerOne";
        String answerTwo = "rightAnswerTwo";

        when(ioConsoleUtil.read()).thenReturn(answerOne, answerTwo);
        doNothing().when(ioConsoleUtil).println(questionOne);
        doNothing().when(ioConsoleUtil).println(questionTextOne);
        doNothing().when(ioConsoleUtil).println(answersTextOne);
        doNothing().when(ioConsoleUtil).println(questionTwo);
        doNothing().when(ioConsoleUtil).println(questionTextTwo);
        doNothing().when(ioConsoleUtil).println(answersTextTwo);

        List<QuizResult> actual = subj.startQuiz(questions);
        //then
        assertEquals(expected, actual);

        verify(ioConsoleUtil, times(1)).println(questionOne);
        verify(ioConsoleUtil, times(1)).println(questionTextOne);
        verify(ioConsoleUtil, times(1)).println(answersTextOne);
        verify(ioConsoleUtil, times(1)).println(questionTwo);
        verify(ioConsoleUtil, times(1)).println(questionTextTwo);
        verify(ioConsoleUtil, times(1)).println(answersTextTwo);
    }
}