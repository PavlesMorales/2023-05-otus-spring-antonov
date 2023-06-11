package ru.otus.spring.homework.repository.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.homework.model.Answer;
import ru.otus.spring.homework.model.Question;
import ru.otus.spring.homework.exceptions.CsvParserException;
import ru.otus.spring.homework.repository.QuestionMapper;

import java.util.List;

@Component
public class CsvToQuestionMapper implements QuestionMapper {

    @Override
    public Question readValue(String string) {


        try {
            String[] csvValues = string.split(",");

            return new Question(csvValues[0].trim(), List.of(
                    new Answer(true, csvValues[1].trim()),
                    new Answer(false, csvValues[2].trim()),
                    new Answer(false, csvValues[3].trim()),
                    new Answer(false, csvValues[4].trim())));

        } catch (RuntimeException e) {
            throw new CsvParserException("Error parse csv string: " + string);
        }
    }
}
