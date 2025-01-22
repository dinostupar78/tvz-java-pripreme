package hr.javafx.restaurant.repositoryFile;

import hr.javafx.restaurant.model.Category;
import hr.javafx.restaurant.model.Ingredient;
import hr.javafx.restaurant.model.Meal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a repository for managing {@link Meal} objects.
 * This class provides methods to retrieve meals from a data source.
 * @param <T> a type parameter that extends {@link Meal}.
 */

public class MealFileRepository<T extends Meal> extends AbstractFileRepository<T> {
    private static final String MEALS_FILE_PATH = "dat/meals.txt";
    private static final Integer NUMBER_OF_ROWS_PER_MEALS = 6;

    public CategoryFileRepository<Category> categoryRepository;

    public MealFileRepository(CategoryFileRepository<Category> categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public T findById(Long id) {
        return findAll().stream()
                .filter(meal -> meal.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
   public Set<T> findAll() {
        Set<T> meals = new HashSet<>();
        try{
            Stream<String> stream = Files.lines(Path.of(MEALS_FILE_PATH));
            List<String> fileRows = stream.collect(Collectors.toList());

            for(Integer i = 0; i < (fileRows.size() / NUMBER_OF_ROWS_PER_MEALS); i++){
                Long id = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_MEALS));
                String name = fileRows.get(i * NUMBER_OF_ROWS_PER_MEALS + 1);

                Long categoryId = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_MEALS + 2));
                Category category = categoryRepository.findById(categoryId);

                String ingredientsIds = fileRows.get(i * NUMBER_OF_ROWS_PER_MEALS + 3);

                IngredientFileRepository ingredientRepository = new IngredientFileRepository(categoryRepository);
                Set<Ingredient> ingredients = Arrays.stream(ingredientsIds.split(","))
                        .map(idString -> Long.parseLong(idString))
                        .map(idLong -> ingredientRepository.findById(idLong))
                        .collect(Collectors.toSet());

                BigDecimal price = BigDecimal.valueOf(Double.parseDouble(fileRows.get(i * NUMBER_OF_ROWS_PER_MEALS + 4)));
                Integer calories = Integer.parseInt(fileRows.get(i * NUMBER_OF_ROWS_PER_MEALS + 5));

                Meal meal = new Meal(id, name, category, ingredients, price, calories);
                meals.add((T) meal);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return meals;
    }

    @Override
    public void save(Set<T> entities) {
        try(PrintWriter writer = new PrintWriter(MEALS_FILE_PATH)){
            for(T entity : entities){
                writer.println(entity.getId());
                writer.println(entity.getName());
                writer.println(entity.getCategory().getId());

                int countIngredients = 0;
                StringBuilder ingredientsIdBuilder = new StringBuilder();
                for(Ingredient ingredient : entity.getIngredient()){
                    ingredientsIdBuilder.append(ingredient.getId());
                    countIngredients++;
                    if(countIngredients < entity.getIngredient().size()){
                        ingredientsIdBuilder.append(",");
                    }
                }

                writer.println(ingredientsIdBuilder);
                writer.println(entity.getPrice());
                writer.println(entity.getCalories());
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to save entities to file: " + MEALS_FILE_PATH, e);
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


