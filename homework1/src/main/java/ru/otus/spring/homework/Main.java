package ru.otus.spring.homework;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.homework.service.Quiz;

public class Main {

    public static void main(String[] args) {

        var context = new ClassPathXmlApplicationContext("/spring-context.xml");
        Quiz bean = context.getBean(Quiz.class);
        bean.startQuiz();
    }
}
