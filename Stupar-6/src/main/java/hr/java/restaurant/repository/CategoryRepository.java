package hr.java.restaurant.repository;
import hr.java.restaurant.model.Category;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a repository for managing {@link Category} objects.
 * This class provides methods to retrieve, save, and manage categories from a data source.
 * @param <T> a type parameter that extends {@link Category}.
 */

public class CategoryRepository <T extends Category> extends AbstractRepository<T>{

    private static final String CATEGORIES_FILE_PATH = "dat/categories.txt";
    private static final Integer NUMBER_OF_ROWS_PER_CATEGORY = 3;

    @Override
    public T findById(Long id) {
        return findAll().stream()
                .filter(category -> category.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Set<T> findAll() {
        Set<T> categories = new HashSet<>();
        try{
            Stream<String> stream = Files.lines(Path.of(CATEGORIES_FILE_PATH));
            List<String> fileRows = stream.collect(Collectors.toList());

            for(Integer i = 0; i < (fileRows.size() / NUMBER_OF_ROWS_PER_CATEGORY); i++){
                Long id = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_CATEGORY));
                String name = fileRows.get(i * NUMBER_OF_ROWS_PER_CATEGORY + 1);
                String description = fileRows.get(i * NUMBER_OF_ROWS_PER_CATEGORY + 2);

                Category category = new Category(id, name, description);
                categories.add((T) category);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    @Override
    public void save(List<T> entities) {
        try(PrintWriter writer = new PrintWriter(CATEGORIES_FILE_PATH);) {
            for(T entity : entities){
                writer.println(entity.getId());
                writer.println(entity.getName());
                writer.println(entity.getDescription());
            }
            writer.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
