package de.throsenheim.unlimited.stockeasilyapi.repository;

public interface RelationRepository<T, ID> {

    boolean exists(T relation);

    T find(T relation);

}
