package hr.javafx.restaurant.repository;

import hr.javafx.restaurant.model.Category;
import hr.javafx.restaurant.model.Ingredient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a repository for managing {@link Ingredient} objects.
 * This class provides methods for retrieving, saving, and managing ingredients from a data source.
 * @param <T> <T> a type parameter that extends {@link Ingredient}.
 */

public class IngredientRepository <T extends Ingredient> extends AbstractRepository<T>{

    private static final String INGREDIENTS_FILE_PATH = "dat/ingredients.txt";
    private static final Integer NUMBER_OF_ROWS_PER_INGREDIENT = 5;
    public CategoryRepository<Category> categoryRepository;

    public IngredientRepository(CategoryRepository<Category> categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public T findById(Long id) {
        return findAll().stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Set<T> findAll() {
        Set<T> ingredients = new HashSet<>();

        try{
            Stream<String> stream = Files.lines(Path.of(INGREDIENTS_FILE_PATH));
            List<String> fileRows = stream.collect(Collectors.toList());

            for(Integer i = 0; i < (fileRows.size() / NUMBER_OF_ROWS_PER_INGREDIENT); i++){
                Long id = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_INGREDIENT));
                String name = fileRows.get(i * NUMBER_OF_ROWS_PER_INGREDIENT + 1);

                Long categoryId = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_INGREDIENT + 2));
                Category category = categoryRepository.findById(categoryId);

                BigDecimal kcal = BigDecimal.valueOf(Double.parseDouble(fileRows.get(i * NUMBER_OF_ROWS_PER_INGREDIENT + 3)));
                String preparationMethod = fileRows.get(i * NUMBER_OF_ROWS_PER_INGREDIENT + 4);

                Ingredient ingredient = new Ingredient(id, name, category, kcal, preparationMethod);
                ingredients.add((T) ingredient);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ingredients;
    }

    @Override
    public void save(List<T> entities) {
        try(PrintWriter writer = new PrintWriter(INGREDIENTS_FILE_PATH)) {
            for(T entity : entities){
                writer.println(entity.getId());
                writer.println(entity.getName());
                writer.println(entity.getCategory().getId());
                writer.println(entity.getKcal());
                writer.println(entity.getPreparationMethod());
            }
            writer.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to save entities to file: " + INGREDIENTS_FILE_PATH, e);
        }
    }
}
