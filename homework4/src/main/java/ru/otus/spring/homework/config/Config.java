package ru.otus.spring.homework.config;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;
import ru.otus.spring.homework.util.IOService;
import ru.otus.spring.homework.util.impl.IOServiceImpl;

@EnableConfigurationProperties(AppProperties.class)
@Configuration
public class Config {

    @Bean
    IOService ioConsoleUtil() {
        return new IOServiceImpl(System.in, System.out);
    }

    @Bean
    public PromptProvider myPromptProvider() {
        return () -> new AttributedString("my-shell:>", AttributedStyle.BOLD.foreground(AttributedStyle.BLUE));
    }
}
