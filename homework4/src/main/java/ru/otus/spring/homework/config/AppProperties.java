package ru.otus.spring.homework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.otus.spring.homework.util.LocaleProvider;
import ru.otus.spring.homework.util.MinimalCountRightAnswersProvider;
import ru.otus.spring.homework.util.ResourceNameProvider;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
public class AppProperties implements LocaleProvider,
        ResourceNameProvider,
        MinimalCountRightAnswersProvider {
    private final int minimalCountRightAnswers;

    private final String pathToQuestions;

    private final Locale locale;


    public AppProperties(int minimalCountRightAnswers, String pathToQuestions, Locale locale) {
        this.minimalCountRightAnswers = minimalCountRightAnswers;
        this.pathToQuestions = pathToQuestions;
        this.locale = locale;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public int getMinimalRightCountAnswers() {
        return minimalCountRightAnswers;
    }

    @Override
    public String getResourceName() {
        return pathToQuestions;
    }
}
