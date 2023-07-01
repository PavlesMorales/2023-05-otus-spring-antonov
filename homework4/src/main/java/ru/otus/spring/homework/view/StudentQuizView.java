package ru.otus.spring.homework.view;

import jakarta.validation.constraints.Size;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import ru.otus.spring.homework.model.Question;
import ru.otus.spring.homework.model.Student;
import ru.otus.spring.homework.repository.QuestionRepository;
import ru.otus.spring.homework.repository.StudentRepository;
import ru.otus.spring.homework.service.StudentQuizService;
import ru.otus.spring.homework.util.MessageSourceProvider;

import java.util.List;

@ShellComponent
public class StudentQuizView {

    private final StudentQuizService studentQuizService;

    private final StudentRepository studentRepository;

    private final QuestionRepository questionRepository;

    private final MessageSourceProvider messageSourceProvider;

    public StudentQuizView(StudentQuizService studentQuizService,
                           StudentRepository studentRepository,
                           QuestionRepository questionRepository,
                           MessageSourceProvider messageSourceProvider) {

        this.studentQuizService = studentQuizService;
        this.studentRepository = studentRepository;
        this.questionRepository = questionRepository;
        this.messageSourceProvider = messageSourceProvider;
    }

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption
                        @Size(min = 2, max = 40) String firstName,
                        @Size(min = 2, max = 40) String lastName) {

        var currentStudent = new Student(firstName, lastName);
        studentRepository.save(currentStudent);

        return messageSourceProvider.getMessage("user.hello")
                .formatted(currentStudent.firstName(), currentStudent.lastName());
    }

    @ShellMethod(value = "Start quiz command", key = {"quiz", "start"})
    @ShellMethodAvailability(value = "isStartQuizAvailable")
    public String startQuiz() {
        Student currentStudent = studentRepository.getStudent();
        List<Question> allQuestionsInRandomOrder = questionRepository.findAllQuestionsInRandomOrder();
        studentQuizService.startStudentQuiz(currentStudent, allQuestionsInRandomOrder);

        return messageSourceProvider.getMessage("user.goodbye")
                .formatted(currentStudent.firstName(), currentStudent.lastName());
    }

    private Availability isStartQuizAvailable() {
        Student currentStudent = studentRepository.getStudent();
        String reason = messageSourceProvider.getMessage("quiz.reason");
        return currentStudent == null ? Availability.unavailable(reason) : Availability.available();
    }
}
