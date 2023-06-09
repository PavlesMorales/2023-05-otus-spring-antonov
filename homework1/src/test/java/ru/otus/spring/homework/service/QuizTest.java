package ru.otus.spring.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.homework.model.Answer;
import ru.otus.spring.homework.model.Question;
import ru.otus.spring.homework.repository.QuestionRepository;
import ru.otus.spring.homework.util.Printable;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizTest {

    @Mock
    Printable printable;

    @Mock
    QuestionRepository repository;

    @InjectMocks
    Quiz subj;


    @Test
    void startQuiz() {
        List<Question> mockQuestions = List.of(new Question("5+5=", List.of(new Answer(true, "10"), new Answer(false, "12"))));
        String firstPrint = "Start quiz!!";
        String secondPrint = "Question 1 : ";
        String thirdPrint = "5+5=";
        String fourPrint = "10" + System.lineSeparator() + "12";
        String fifthPrint = "";
        String sixPrint = "Stop quiz";

        when(repository.findAllQuestions()).thenReturn(mockQuestions);
        doNothing().when(printable).println(firstPrint);
        doNothing().when(printable).println(secondPrint);
        doNothing().when(printable).println(thirdPrint);
        doNothing().when(printable).println(fourPrint);
        doNothing().when(printable).println(fifthPrint);
        doNothing().when(printable).println(sixPrint);
        subj.startQuiz();

        verify(repository, times(1)).findAllQuestions();
        verify(printable, times(1)).println(firstPrint);
        verify(printable, times(1)).println(secondPrint);
        verify(printable, times(1)).println(thirdPrint);
        verify(printable, times(1)).println(fourPrint);
        verify(printable, times(1)).println(fifthPrint);
        verify(printable, times(1)).println(sixPrint);
    }
}