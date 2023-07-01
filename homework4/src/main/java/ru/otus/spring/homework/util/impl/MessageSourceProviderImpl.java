package ru.otus.spring.homework.util.impl;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.spring.homework.util.LocaleProvider;
import ru.otus.spring.homework.util.MessageSourceProvider;

@Component
public class MessageSourceProviderImpl implements MessageSourceProvider {

    private final MessageSource messageSource;

    private final LocaleProvider localeProvider;

    public MessageSourceProviderImpl(MessageSource messageSource, LocaleProvider localeProvider) {
        this.messageSource = messageSource;
        this.localeProvider = localeProvider;
    }

    @Override
    public String getMessage(String key) {
        return messageSource.getMessage(key, null, localeProvider.getLocale());
    }

    @Override
    public String getMessageWithArgs(String key, Object[] args) {
        return messageSource.getMessage(key, args, localeProvider.getLocale());
    }
}
