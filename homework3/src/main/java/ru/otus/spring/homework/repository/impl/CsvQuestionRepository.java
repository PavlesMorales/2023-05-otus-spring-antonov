package ru.otus.spring.homework.repository.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.homework.exceptions.CsvParserException;
import ru.otus.spring.homework.exceptions.ResourceReaderException;
import ru.otus.spring.homework.model.Answer;
import ru.otus.spring.homework.model.Question;
import ru.otus.spring.homework.repository.QuestionRepository;
import ru.otus.spring.homework.util.QuestionsShuffle;
import ru.otus.spring.homework.util.ResourceNameProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvQuestionRepository implements QuestionRepository {

    private final ResourceNameProvider resourceNameProvider;

    private final QuestionsShuffle questionsShuffle;

    public CsvQuestionRepository(ResourceNameProvider resourceNameProvider, QuestionsShuffle questionsShuffle) {
        this.resourceNameProvider = resourceNameProvider;
        this.questionsShuffle = questionsShuffle;
    }

    @Override
    public List<Question> findAllQuestions() {
        return readResourceAsStrings();
    }

    @Override
    public List<Question> findAllQuestionsInRandomOrder() {
        return questionsShuffle.shuffleQuestions(readResourceAsStrings());

    }

    private List<Question> readResourceAsStrings() {
        List<Question> result = new ArrayList<>();
        try (var in = getClass().getResourceAsStream(resourceNameProvider.getResourceName())) {

            if (in == null) {
                throw new ResourceReaderException("Resource is null");
            }

            var reader = new BufferedReader(new InputStreamReader(in));

            String ignoreHeaders = reader.readLine();

            while (reader.ready()) {
                result.add(readValue(reader.readLine()));
            }

            return result;
        } catch (IOException e) {
            throw new ResourceReaderException("Error read csv file from resources");
        }
    }

    private Question readValue(String string) {

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
