package hr.java.production.main;

import hr.java.restaurant.model.Category;
import hr.java.restaurant.model.Ingredient;
import hr.java.restaurant.model.Meal;
import hr.java.restaurant.repository.CategoryRepository;
import hr.java.restaurant.repository.IngredientRepository;
import hr.java.restaurant.repository.MealsRepository;

import java.util.List;

public class FilesMain {
    public static void main(String[] args) {

        CategoryRepository<Category> categoryRepository = new CategoryRepository<>();
        Category category = categoryRepository.findById(1L);
        System.out.println(category.getName());

        IngredientRepository<Ingredient> ingredientRepository = new IngredientRepository<>(categoryRepository);
        Ingredient ingredient = ingredientRepository.findById(1L);
        System.out.println(ingredient.getName());


        MealsRepository<Meal> mealsRepository = new MealsRepository<>(categoryRepository);
        Meal meal = mealsRepository.findById(2L);
        List<Meal> meals = mealsRepository.findAll();
        System.out.println(meal.getName());
        meals.forEach(System.out::println);



    }
}
