package ru.otus.spring.homework.repository.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.spring.homework.TestConfig;
import ru.otus.spring.homework.model.Answer;
import ru.otus.spring.homework.model.Question;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CsvQuestionRepositoryTest extends TestConfig {

    @Autowired
    CsvQuestionRepository subj;

    @Test
    void findAllQuestions() {

        Question firstQuestion = new Question("What is a correct syntax to output \"Hello World\" in Java?",
                List.of(
                        new Answer(true, "System.out.println(\"Hello World\");"),
                        new Answer(false, "Console.WriteLine(\"Hello World\");"),
                        new Answer(false, "print(\"Hello World\");"),
                        new Answer(false, "echo(\"Hello World\");")
                ));
        Question secondQuestion = new Question("What is the correct way to create an object called myObj of MyClass?",
                List.of(
                        new Answer(true, "MyClass myObj = new MyClass();"),
                        new Answer(false, "class MyClass = new myObj();"),
                        new Answer(false, "class myObj = new MyClass();"),
                        new Answer(false, "new myObj = MyClass();")
                ));

        List<Question> expected = List.of(firstQuestion, secondQuestion);


        List<Question> actual = subj.findAllQuestions();

        assertEquals(expected, actual);


    }
}