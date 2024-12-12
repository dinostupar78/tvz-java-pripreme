package hr.javafx.restaurant.repository;

import hr.javafx.restaurant.model.Entity;

import java.util.List;
import java.util.Set;

public abstract class AbstractRepository<T extends Entity> {
    abstract T findById(Long id);
    abstract Set<T> findAll();
    abstract void save(List<T> entities);

}
