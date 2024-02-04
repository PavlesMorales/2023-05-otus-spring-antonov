package ru.otus.spring.homework.repositories;

import ru.otus.spring.homework.models.EntityId;

import java.util.Optional;

public interface Repository<E extends EntityId<I>, I> {

    Optional<E> findById(I id);

    E save(E entity);

    void remove(E entity);

}
