package hr.java.production.main;

import hr.java.restaurant.generics.RestaurantLabourExchangeOffice;
import hr.java.restaurant.model.*;
import hr.java.restaurant.repository.*;
import hr.java.utils.ComparatorUtils;
import hr.java.utils.LambdaUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static hr.java.utils.MealRestaurantUtils.mapCitiesToRestaurants;
import static hr.java.utils.MealRestaurantUtils.mapMealsToRestaurants;

public class FilesMain {
    public static void main(String[] args) {

        CategoryRepository<Category> categoryRepository = new CategoryRepository<>();
        IngredientRepository<Ingredient> ingredientRepository = new IngredientRepository<>(categoryRepository);
        MealsRepository<Meal> mealsRepository = new MealsRepository<>(categoryRepository);
        ContractRepository<Contract> contractRepository = new ContractRepository<>();
        ChefRepository<Chef> chefRepository = new ChefRepository<>(contractRepository);
        WaiterRepository<Waiter> waiterRepository = new WaiterRepository<>(contractRepository);
        DelivererRepository<Deliverer> delivererRepository = new DelivererRepository<>(contractRepository);
        AddressRepository<Address> addressRepository = new AddressRepository<>();
        RestaurantRepository<Restaurant> restaurantRepository = new RestaurantRepository<>(addressRepository, mealsRepository, chefRepository, waiterRepository, delivererRepository);
        OrderRepository<Order> orderRepository = new OrderRepository<>(restaurantRepository, mealsRepository, delivererRepository);


        Set<Meal> meals = mealsRepository.findAll();
        Set<Restaurant> restaurants = restaurantRepository.findAll();
        Set<Chef> chefs = chefRepository.findAll();
        Set<Waiter> waiters = waiterRepository.findAll();
        Set<Deliverer> deliverers = delivererRepository.findAll();
        List<Order> orders = new ArrayList<>(orderRepository.findAll());

        // Convert Set to List where necessary
        List<Restaurant> restaurantList = new ArrayList<>(restaurants); // Convert Set to List

        RestaurantLabourExchangeOffice<Restaurant> restaurantLabourExchangeOffice = new RestaurantLabourExchangeOffice<>(restaurantList);
        Map<Meal, RestaurantLabourExchangeOffice<Restaurant>> mealRestaurantMap = mapMealsToRestaurants(restaurantLabourExchangeOffice);

        ComparatorUtils.printHighestPaidEmployeeInRestaurants(restaurantLabourExchangeOffice);
        ComparatorUtils.printHighestEmployeedEmployeeInRestaurants(restaurantLabourExchangeOffice);

        ComparatorUtils.printMealsSortedByRestaurantCount(mealRestaurantMap);
        ComparatorUtils.printSortedIngredientsAlphabetically(meals);

        LambdaUtils.findAndPrintRestaurantWithMostEmployees(restaurantList);

        LambdaUtils.findAndPrintMostPopularMeal(mealRestaurantMap);

        LambdaUtils.printIngredientsForAllOrders(orders);

        LambdaUtils.calculateTotalPrice(orders);

        Map<String, RestaurantLabourExchangeOffice<Restaurant>> mealRestaurantCity = mapCitiesToRestaurants(restaurantLabourExchangeOffice);
        LambdaUtils.groupRestaurantsByCity(restaurantLabourExchangeOffice);

        mealRestaurantCity.forEach((city, office) -> {
            System.out.println("\nCity: " + city);
            System.out.println("Restaurants: ");
            office.getRestaurants().forEach(restaurant -> System.out.println(restaurant));
        });









    }
}

