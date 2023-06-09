package ru.otus.spring.homework.repository.impl;

import ru.otus.spring.homework.model.Question;
import ru.otus.spring.homework.repository.QuestionRepository;
import ru.otus.spring.homework.repository.ResourceReadable;

import java.util.List;

public class CsvQuestionRepository implements QuestionRepository {

    private final ResourceReadable reader;

    private final CsvToQuestionMapper mapper;

    public CsvQuestionRepository(ResourceReadable reader, CsvToQuestionMapper mapper) {
        this.reader = reader;
        this.mapper = mapper;
    }

    @Override
    public List<Question> findAllQuestions() {
        return reader.readResourceAsStrings().stream().map(mapper::readValue).toList();
    }
}
