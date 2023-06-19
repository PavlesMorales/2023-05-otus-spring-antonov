package ru.otus.spring.homework.util.impl;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.spring.homework.config.AppProperties;
import ru.otus.spring.homework.util.MessageSourceUtil;

import java.util.Locale;

@Component
public class MessageSourceUtilImpl implements MessageSourceUtil {

    private final MessageSource messageSource;

    private final Locale locale;

    public MessageSourceUtilImpl(MessageSource messageSource, AppProperties properties) {
        this.messageSource = messageSource;
        this.locale = properties.locale();
    }


    @Override
    public String getMessage(String key) {
        return messageSource.getMessage(key, null, locale);
    }

    @Override
    public String getMessageWithArgs(String key, Object[] args) {
        return messageSource.getMessage(key, args, locale);
    }
}
