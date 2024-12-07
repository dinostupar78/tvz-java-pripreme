package hr.java.restaurant.repository;

import hr.java.restaurant.model.Entity;

import java.util.List;

public abstract class AbstractRepository<T extends Entity> {

    abstract T findById(Long id);
    abstract List<T> findAll();
    abstract void save(List<T> entities);

}
