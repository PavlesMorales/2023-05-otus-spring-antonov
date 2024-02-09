package ru.otus.spring.homework;


import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

@DataMongoTest
@EnableMongock
@EnableConfigurationProperties
@ComponentScan({"ru.otus.spring.homework.repositories", "ru.otus.spring.homework.changelog"})
public class ApplicationTestConfig {

}