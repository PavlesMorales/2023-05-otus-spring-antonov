package ru.otus.spring.homework.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.spring.homework.TestConfig;
import ru.otus.spring.homework.model.*;
import ru.otus.spring.homework.repository.QuestionRepository;
import ru.otus.spring.homework.repository.StudentRepository;
import ru.otus.spring.homework.service.StudentQuizService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentQuizConsoleViewTest {

    @Mock
    StudentQuizService studentQuizService;

    @Mock
    StudentRepository studentRepository;

    @Mock
    QuestionRepository questionRepository;
    @InjectMocks
    StudentQuizConsoleView subj;

    @Test
    void run() {

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

        subj.run();

        verify(studentRepository, times(1)).getStudent();
        verify(questionRepository, times(1)).findAllQuestionsInRandomOrder();
        verify(studentQuizService, times(1)).startStudentQuiz(student, questions);

    }
}