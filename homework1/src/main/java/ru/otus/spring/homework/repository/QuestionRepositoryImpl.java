package ru.otus.spring.homework.repository;

import ru.otus.spring.homework.util.impl.CsvToQuestionMapper;
import ru.otus.spring.homework.model.Question;
import ru.otus.spring.homework.util.ResourceReadable;

import java.util.List;

public class QuestionRepositoryImpl implements QuestionRepository {

    private final ResourceReadable reader;

    private final CsvToQuestionMapper mapper;

    public QuestionRepositoryImpl(ResourceReadable reader, CsvToQuestionMapper mapper) {
        this.reader = reader;
        this.mapper = mapper;
    }

    @Override
    public List<Question> findAllQuestions() {
        return reader.readResourceAsStrings().stream().map(mapper::readValue).toList();
    }
}
