package ru.otus.spring.homework.util.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.homework.model.Answer;
import ru.otus.spring.homework.model.Question;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuestionsShuffleImplTest {

    @InjectMocks
    QuestionsShuffleImpl subj;

    @Test
    void shuffleQuestions_shouldReturnSameListInRandomOrder() {
        List<Question> expected = List.of(
                new Question("question one", List.of(
                        new Answer(false, "wrongAnswerOne"),
                        new Answer(true, "rightAnswerTwo")
                )),
                new Question("question two", List.of(
                        new Answer(true, "rightAnswerTwo"),
                        new Answer(false, "wrongAnswerOne")
                )));

        List<Question> actual = subj.shuffleQuestions(expected);

        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());

    }

    @Test
    void shuffleQuestions_shouldThrowNPE() {
        assertThrows(NullPointerException.class, () -> subj.shuffleQuestions(null));
    }

    @Test
    void shuffleQuestions_shouldReturnEmptyList() {
        List<Question> actual = subj.shuffleQuestions(new ArrayList<>());
        assertTrue(actual.isEmpty());
    }
}