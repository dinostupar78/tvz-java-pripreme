package hr.javafx.restaurant.repository;

import hr.javafx.restaurant.model.Category;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Long.parseLong;

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

    @Override
    public Set<T> findAll() {
        Set<T> categories = new HashSet<>();
        try {
            // Read all lines from the file
            Stream<String> stream = Files.lines(Path.of(CATEGORIES_FILE_PATH));
            List<String> fileRows = stream.collect(Collectors.toList());

            // Loop through the file rows in chunks of 5 (one category per 5 rows)
            for (int i = 0; i < fileRows.size(); i += NUMBER_OF_ROWS_PER_CATEGORY) {
                try {
                    // Ensure that there are enough lines for a valid category
                    if (i + NUMBER_OF_ROWS_PER_CATEGORY > fileRows.size()) {
                        break;  // Exit if there are not enough lines for another category
                    }

                    // Parse each field from the file
                    Long id = parseLong(fileRows.get(i));  // Category ID
                    String name = fileRows.get(i + 1); // Category Name
                    String description = fileRows.get(i + 2); // Category Description
                    boolean isActive = parseBoolean(fileRows.get(i + 3)); // Category Active status
                    String categoryType = fileRows.get(i + 4); // Category Type

                    // Create the category object and add it to the set
                    Category category = new Category(id, name, description, isActive, categoryType);
                    categories.add((T) category);
                } catch (Exception e) {
                    // Log or handle the error (e.g., malformed data)
                    System.out.println("Error parsing category fields: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading categories file", e);
        }
        return categories;
    }

    @Override
    public void save(Set<T> entities) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(CATEGORIES_FILE_PATH, true))) {  // Open in append mode
            for (T entity : entities) {
                Category category = (Category) entity;

                // Write all 5 fields to the file: ID, Name, Description, Active, CategoryType
                writer.println(category.getId());
                writer.println(category.getName());
                writer.println(category.getDescription());
                writer.println(category.isActive());  // Is Active
                writer.println(category.getCategoryType());  // Category Type
            }
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void save(T entity) {
        Set<T> entities = findAll();
        if(Optional.ofNullable(entity.getId()).isEmpty()){
            entity.setId(generateNewId());
        }
        entities.add(entity);
        save(entities);

    }

    private Long generateNewId(){
        return findAll().stream().map(b -> b.getId())
                .max((i1, i2) -> i1.compareTo(i2)).orElse(1l) + 1;
    }
}
