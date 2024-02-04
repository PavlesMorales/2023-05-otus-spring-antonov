package ru.otus.spring.homework.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import ru.otus.spring.homework.models.EntityId;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractRepository<E extends EntityId<I>, I> implements Repository<E, I> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<E> findById(final I id) {
        return Optional.ofNullable(em.find(getEntityType(), id));
    }

    @Override
    public E save(final E e) {
        if (Objects.isNull(e.getId())) {
            em.persist(e);
            return e;
        }
        return em.merge(e);
    }

    @Override
    public void remove(final E entity) {
        em.remove(entity);
    }

    protected EntityManager getEm() {
        return em;
    }

    protected abstract Class<E> getEntityType();

}
