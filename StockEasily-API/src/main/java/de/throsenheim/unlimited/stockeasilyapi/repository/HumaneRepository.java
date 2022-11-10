package de.throsenheim.unlimited.stockeasilyapi.repository;

import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface HumaneRepository<T, ID> extends Repository<T, ID> {

    // Enable and implement when needed or throw new NotImplementedException otherwise

//    ID count();

//    void deleteAll();

//    void deleteAll(Iterable<T> entities);

//    void delete(T entity);

//    void deleteById(ID id);

//    boolean existsById(ID id);

//    Iterable<T> findAll();

    Iterable<T> findAllById(Iterable<ID> ids);

    Optional<T> findById(ID id);

    T findByName(String name);

    Iterable<T> saveAll(Iterable<T> entities);

    T save(T entity);

    T save(T entity, boolean commit);
}
