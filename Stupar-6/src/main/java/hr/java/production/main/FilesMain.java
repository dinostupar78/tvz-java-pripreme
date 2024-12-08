package hr.java.production.main;

import hr.java.restaurant.model.*;
import hr.java.restaurant.repository.*;

import java.util.List;

public class FilesMain {
    public static void main(String[] args) {

        CategoryRepository<Category> categoryRepository = new CategoryRepository<>();
        Category category = categoryRepository.findById(1L);
        System.out.println(category.getName());

        IngredientRepository<Ingredient> ingredientRepository = new IngredientRepository<>(categoryRepository);
        Ingredient ingredient = ingredientRepository.findById(1L);
        System.out.println(ingredient.getName());
        List<Ingredient> ingredients = ingredientRepository.findAll();


        MealsRepository<Meal> mealsRepository = new MealsRepository<>(categoryRepository);
        Meal meal = mealsRepository.findById(2L);
        List<Meal> meals = mealsRepository.findAll();
        System.out.println(meal.getName());
        meals.forEach(System.out::println);

        ContractRepository<Contract> contractRepository = new ContractRepository<>();
        Contract contract = contractRepository.findById(2L);
        System.out.println(contract.getContractType());

        ChefRepository<Chef> chefRepository = new ChefRepository<>(contractRepository);
        Chef chef = chefRepository.findById(1L);
        System.out.println(chef.getFirstName());

        WaiterRepository<Waiter> waiterRepository = new WaiterRepository<>(contractRepository);
        Waiter waiter = waiterRepository.findById(1L);
        System.out.println(waiter.getFirstName());

        DelivererRepository<Deliverer> delivererRepository = new DelivererRepository<>(contractRepository);
        Deliverer deliverer = delivererRepository.findById(1L);
        System.out.println(deliverer.getFirstName());

        AddressRepository<Address> addressRepository = new AddressRepository<>();
        Address address = addressRepository.findById(1L);
        System.out.println(address.getStreet());

        RestaurantRepository<Restaurant> restaurantRepository = new RestaurantRepository<>(addressRepository, mealsRepository, chefRepository, waiterRepository, delivererRepository);
        Restaurant restaurant = restaurantRepository.findById(3L);
        System.out.println(restaurant.getMeals());



    }
}
