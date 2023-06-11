package ru.otus.spring.homework;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.homework.service.StudentQuizService;

@ComponentScan
public class Main {

    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(Main.class);
        StudentQuizService bean = context.getBean(StudentQuizService.class);
        bean.startStudentQuiz();
    }
}
