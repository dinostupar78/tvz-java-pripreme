package hr.javafx.restaurant.repository;

import hr.javafx.restaurant.model.Entity;

import java.util.Set;

public abstract class AbstractRepository<T extends Entity> {
    public abstract T findById(Long id);
    public abstract Set<T> findAll();
    public abstract void save(Set<T> entities);
    public abstract void save(T entity);


}
