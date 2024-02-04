package ru.otus.spring.homework.repositories.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.homework.models.entity.Book;
import ru.otus.spring.homework.repositories.AbstractRepository;
import ru.otus.spring.homework.repositories.BookRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaBookRepository extends AbstractRepository<Book, Long> implements BookRepository {

    @Override
    public List<Book> findAll() {
        return getEm().createQuery(
                        """
                                select b from Book b
                                    left join fetch b.genre
                                    left join fetch b.author
                                """, Book.class)
                .getResultList();

    }

    @Override
    protected Class<Book> getEntityType() {
        return Book.class;
    }
}
