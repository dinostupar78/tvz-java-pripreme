package hr.java.production.main;

import hr.java.restaurant.enums.ContractType;
import hr.java.restaurant.generics.RestaurantLabourExchangeOffice;
import hr.java.restaurant.model.*;
import hr.java.utils.ComparatorUtils;
import hr.java.utils.LambdaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static hr.java.production.main.Main.*;
import static hr.java.utils.MealRestaurantUtils.displayRestaurantsForSelectedMeal;
import static hr.java.utils.MealRestaurantUtils.mapMealsToRestaurants;

public class DummyMain {
    public static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // Dummy data for categories, ingredients, meals, etc.
        List<Category> categories = new ArrayList<>();
        Set<Ingredient> ingredients = new HashSet<>();
        Set<Meal> meals = new HashSet<>();
        Set<Chef> chefs = new HashSet<>();
        Set<Waiter> waiters = new HashSet<>();
        Set<Deliverer> deliverers = new HashSet<>();
        List<Restaurant> restaurants = new ArrayList<>();
        List<Address> addresses = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        List<Person> employees = new ArrayList<>();
        List<Meal> specialMeals = new ArrayList<>();
        Map<Meal, RestaurantLabourExchangeOffice<Restaurant>> mealRestaurantMap = new HashMap<>();

        // Create dummy categories
        Category category = new Category(1L, "Main Course", "Hot meals");
        categories.add(category);

        // Create dummy ingredients
        Ingredient ingredient1 = new Ingredient(1L, "Tomato", category, new BigDecimal("0.50"), "Raw");
        Ingredient ingredient2 = new Ingredient(2L, "Cheese", category, new BigDecimal("1.00"), "Grated");
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);

        // Create dummy meals
        Meal meal1 = new Meal(1L, "Pizza", category, new HashSet<>(Arrays.asList(ingredient1, ingredient2)), new BigDecimal("9.99"), 500);
        Meal meal2 = new Meal(2L, "Burger", category, new HashSet<>(Arrays.asList(ingredient1, ingredient2)), new BigDecimal("7.99"), 700);
        meals.add(meal1);
        meals.add(meal2);

        // Create dummy chefs
        Chef chef = new Chef(1L, "John", "Doe", new Contract(new BigDecimal("2000.00"), LocalDate.now(), LocalDate.now().plusMonths(6), ContractType.FULL_TIME), new Bonus(new BigDecimal("100.00")));
        chefs.add(chef);

        // Create dummy waiters
        Waiter waiter = new Waiter(1L, "Jane", "Smith", new Contract(new BigDecimal("1500.00"), LocalDate.now(), LocalDate.now().plusMonths(12), ContractType.PART_TIME), new Bonus(new BigDecimal("50.00")));
        waiters.add(waiter);

        // Create dummy deliverers
        Deliverer deliverer = new Deliverer(1L, "Mike", "Johnson", new Contract(new BigDecimal("1200.00"), LocalDate.now(), LocalDate.now().plusMonths(6), ContractType.PART_TIME), new Bonus(new BigDecimal("30.00")));
        deliverers.add(deliverer);

        // Create dummy restaurants
        Address address1 = new Address.BuilderAddress(1L)
                .atStreet("Main Street")
                .atHouseNumber("123")
                .atCity("Zagreb")
                .atPostalCode("10000")
                .build();

        System.out.println("Address: " + address1.toString());

        // Creating another Address object with different data
        Address address2 = new Address.BuilderAddress(2L)
                .atStreet("Second Avenue")
                .atHouseNumber("456")
                .atCity("Split")
                .atPostalCode("21000")
                .build();

        System.out.println("Address: " + address2.toString());
        Restaurant restaurant1 = new Restaurant(1L, "Restaurant A", address1, new HashSet<>(Arrays.asList(meal1)),
                new HashSet<>(chefs), new HashSet<>(waiters), new HashSet<>(deliverers));

        Restaurant restaurant2 = new Restaurant(2L, "Restaurant B", address2, new HashSet<>(Arrays.asList(meal2)),
                new HashSet<>(chefs), new HashSet<>(waiters), new HashSet<>(deliverers));

        restaurants.add(restaurant1);
        restaurants.add(restaurant2);

        // Create dummy orders
        Order order = new Order(1L, restaurant1, new ArrayList<>(Arrays.asList(meal1)), deliverer, LocalDateTime.now());
        orders.add(order);

        // Create RestaurantLabourExchangeOffice with restaurants
        RestaurantLabourExchangeOffice<Restaurant> restaurantLabourExchangeOffice = new RestaurantLabourExchangeOffice<>(restaurants);

        // Map meals to restaurants
        mealRestaurantMap = mapMealsToRestaurants(restaurantLabourExchangeOffice);

        // Now, you can use the display method to show restaurants for a selected meal
        Scanner scanner = new Scanner(System.in);
        displayRestaurantsForSelectedMeal(scanner, mealRestaurantMap);

        // Add employees to list
        employees.addAll(chefs);
        employees.addAll(waiters);
        employees.addAll(deliverers);

        // Perform operations like finding highest paid employee, longest contract, etc.
        Person highestPaidEmployee = findHighestPaidEmployee(employees);
        System.out.println("\nHighest Paid Employee:");
        printEmployeeInfo(highestPaidEmployee);

        Person longestContractEmployee = findLongestContractEmployee(employees);
        System.out.println("Employee with the longest contract:");
        printEmployeeInfo(longestContractEmployee);

        printMealWithMinMaxCalories(specialMeals);

        // Other operations
        ComparatorUtils.printHighestPaidEmployeeInRestaurants(restaurantLabourExchangeOffice);

        ComparatorUtils.printHighestEmployeedEmployeeInRestaurants(restaurantLabourExchangeOffice);

        ComparatorUtils.printMealsSortedByRestaurantCount(mealRestaurantMap);

        ComparatorUtils.printSortedIngredientsAlphabetically(meals);

        LambdaUtils.findAndPrintRestaurantWithMostEmployees(restaurants);

        LambdaUtils.findAndPrintMostPopularMeal(mealRestaurantMap);

        LambdaUtils.printIngredientsForAllOrders(orders);

        LambdaUtils.calculateTotalPrice(orders);

        LambdaUtils.groupRestaurantsByCity(restaurantLabourExchangeOffice);





    }
}
