package hr.java.restaurant.repository;

import hr.java.restaurant.model.Category;
import hr.java.restaurant.model.Ingredient;
import hr.java.restaurant.model.Meal;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MealsRepository <T extends Meal> extends AbstractRepository<T>{
    private static final String MEALS_FILE_PATH = "dat/meals.txt";
    private static final Integer NUMBER_OF_ROWS_PER_MEALS = 6;

    public CategoryRepository<Category> categoryRepository;

    public MealsRepository(CategoryRepository<Category> categoryRepository) {
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

                IngredientRepository ingredientRepository = new IngredientRepository(categoryRepository);
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
    void save(List<T> entities) {

    }
}


