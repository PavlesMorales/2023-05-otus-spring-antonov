package ru.otus.spring.homework.event;

import com.mongodb.lang.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.homework.exceptions.EntityDeleteException;
import ru.otus.spring.homework.models.entity.Author;
import ru.otus.spring.homework.repositories.BookRepository;

@Component
@RequiredArgsConstructor
public class BookRepositoryAuthorDeleteEvent extends AbstractMongoEventListener<Author> {

    private final BookRepository repository;

    @Override
    public void onBeforeDelete(@NonNull final BeforeDeleteEvent<Author> event) {
        super.onBeforeDelete(event);
        var source = event.getSource();
        var id = source.get("_id").toString();

        if (repository.existsByAuthorId(id)) {
            throw new EntityDeleteException();
        }
    }
}
