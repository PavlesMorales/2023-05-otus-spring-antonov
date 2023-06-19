package ru.otus.spring.homework.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.homework.util.IOConsoleUtil;
import ru.otus.spring.homework.util.impl.IOConsoleUtilImpl;

@EnableConfigurationProperties(AppProperties.class)
@Configuration
public class Config {

    @Bean
    IOConsoleUtil ioConsoleUtil() {
        return new IOConsoleUtilImpl(System.in, System.out);
    }

}
