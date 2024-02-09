package ru.otus.spring.homework.event;

import com.mongodb.lang.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.homework.exceptions.EntityDeleteException;
import ru.otus.spring.homework.models.entity.Genre;
import ru.otus.spring.homework.repositories.BookRepository;

@Component
@RequiredArgsConstructor
public class GenreDeleteEvent extends AbstractMongoEventListener<Genre> {

    private final BookRepository repository;

    @Override
    public void onBeforeDelete(@NonNull final BeforeDeleteEvent<Genre> event) {
        super.onBeforeDelete(event);
        var source = event.getSource();
        var id = source.get("_id").toString();

        if (repository.existsByGenreId(id)) {
            throw new EntityDeleteException();
        }
    }
}
