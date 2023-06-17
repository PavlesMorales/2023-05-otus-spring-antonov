package ru.otus.spring.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.otus.spring.homework.util.IOConsoleUtil;
import ru.otus.spring.homework.util.impl.IOConsoleUtilImpl;

@PropertySource(value = "classpath:application.yaml")
@Configuration
public class Config {

    @Bean
    static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    IOConsoleUtil ioConsoleUtil() {
        return new IOConsoleUtilImpl(System.in, System.out);
    }

}
