package hr.javafx.restaurant.repositoryDatabase;

import hr.javafx.restaurant.exception.RepositoryAccessException;
import hr.javafx.restaurant.model.Entity;

import java.util.Set;

public abstract class AbstractDatabaseRepository<T extends Entity> {
    public abstract Set<T> findAll() throws RepositoryAccessException;
    public abstract void save(Set<T> entities) throws RepositoryAccessException;
    public abstract void save(T entity) throws RepositoryAccessException;


}
