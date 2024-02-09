package ru.otus.spring.homework.event;

import com.mongodb.lang.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.homework.models.entity.Book;
import ru.otus.spring.homework.repositories.CommentRepository;

@Component
@RequiredArgsConstructor
public class BookDeleteCascadeEvent extends AbstractMongoEventListener<Book> {

    private final CommentRepository repository;

    @Override
    public void onAfterDelete(@NonNull final AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        var source = event.getSource();
        var id = source.get("_id").toString();
        repository.deleteAllByBookId(id);
    }
}
