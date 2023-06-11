package ru.otus.spring.homework.repository.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.homework.model.Answer;
import ru.otus.spring.homework.model.Question;
import ru.otus.spring.homework.repository.ResourceReadable;
import ru.otus.spring.homework.repository.impl.CsvToQuestionMapper;
import ru.otus.spring.homework.repository.impl.CsvQuestionRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CsvQuestionRepositoryTest {

    @Mock
    ResourceReadable reader;

    @Mock
    CsvToQuestionMapper mapper;
    @InjectMocks
    CsvQuestionRepository subj;

    @Test
    void findAllQuestions() {
        String firstString = "first csv string";
        String secondString = "second csv string";
        List<String> resourcesCsvAsStrings = List.of(firstString, secondString);

        Question firstQuestion = new Question("question", List.of(new Answer(true, "answer"), new Answer(false, "someElse")));
        Question secondQuestion = new Question("question 2", List.of(new Answer(true, "answer"), new Answer(false, "wrongAnswer")));

        List<Question> expected = List.of(firstQuestion, secondQuestion);

        when(reader.readResourceAsStrings()).thenReturn(resourcesCsvAsStrings);
        when(mapper.readValue(firstString)).thenReturn(firstQuestion);
        when(mapper.readValue(secondString)).thenReturn(secondQuestion);

        List<Question> actual = subj.findAllQuestions();

        assertEquals(expected, actual);

        verify(reader, times(1)).readResourceAsStrings();
        verify(mapper, times(1)).readValue(firstString);
        verify(mapper, times(1)).readValue(secondString);

    }

    @Test
    void findAllQuestions_shouldReturnEmptyList() {
        List<String> resourcesCsvAsStrings = new ArrayList<>();

        when(reader.readResourceAsStrings()).thenReturn(resourcesCsvAsStrings);

        List<Question> actual = subj.findAllQuestions();
        assertTrue(actual.isEmpty());

        verify(reader, times(1)).readResourceAsStrings();
        verifyNoInteractions(mapper);

    }
}