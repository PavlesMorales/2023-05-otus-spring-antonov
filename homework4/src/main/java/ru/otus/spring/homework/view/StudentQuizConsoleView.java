package ru.otus.spring.homework.view;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.otus.spring.homework.model.Question;
import ru.otus.spring.homework.model.Student;
import ru.otus.spring.homework.repository.QuestionRepository;
import ru.otus.spring.homework.repository.StudentRepository;
import ru.otus.spring.homework.service.StudentQuizService;

import java.util.List;

@Service
@Profile(value = "!test")
public class StudentQuizConsoleView implements CommandLineRunner {

    private final StudentQuizService studentQuizService;

    private final StudentRepository studentRepository;

    private final QuestionRepository questionRepository;

    public StudentQuizConsoleView(StudentQuizService studentQuizService,
                                  StudentRepository studentRepository,
                                  QuestionRepository questionRepository) {

        this.studentQuizService = studentQuizService;
        this.studentRepository = studentRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public void run(String... args) {
        Student student = studentRepository.getStudent();
        List<Question> allQuestionsInRandomOrder = questionRepository.findAllQuestionsInRandomOrder();
        studentQuizService.startStudentQuiz(student, allQuestionsInRandomOrder);
    }
}
