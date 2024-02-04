package ru.otus.spring.homework.commands.converters;

import org.springframework.stereotype.Component;
import ru.otus.spring.homework.models.dto.AuthorDto;

@Component
public class AuthorConverter {

    public String authorToString(final AuthorDto author) {
        return "Id: %d, First name: %s, Last name: %s"
                .formatted(author.getId(), author.getFirstName(), author.getLastName());
    }

}
