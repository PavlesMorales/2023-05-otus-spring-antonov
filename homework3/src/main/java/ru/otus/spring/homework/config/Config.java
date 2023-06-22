package ru.otus.spring.homework.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.homework.util.IOConsoleProvider;
import ru.otus.spring.homework.util.impl.IOConsoleProviderImpl;

@EnableConfigurationProperties(AppProperties.class)
@Configuration
public class Config {

    @Bean
    IOConsoleProvider ioConsoleUtil() {
        return new IOConsoleProviderImpl(System.in, System.out);
    }

}
