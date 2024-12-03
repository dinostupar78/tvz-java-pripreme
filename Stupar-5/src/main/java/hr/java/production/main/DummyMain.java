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
        List<Meal> specialMeals = new ArrayList<>();

        List<Category> categories = new ArrayList<>();
        Category mainCourse = new Category(1L, "Main Course", "Hot meals");
        Category desserts = new Category(2L, "Desserts", "Sweet dishes");
        categories.add(mainCourse);
        categories.add(desserts);

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(new Ingredient(1L, "Tomato", mainCourse, new BigDecimal("0.50"), "Fresh"));
        ingredients.add(new Ingredient(2L, "Cheese", mainCourse, new BigDecimal("1.00"), "Grated"));
        ingredients.add(new Ingredient(3L, "Chocolate", desserts, new BigDecimal("2.00"), "Dark"));

        Set<Meal> meals = new HashSet<>();
        meals.add(new Meal(1L, "Pizza", mainCourse, new HashSet<>(Arrays.asList(
                new Ingredient(1L, "Tomato", mainCourse, new BigDecimal("0.50"), "Fresh"),
                new Ingredient(2L, "Cheese", mainCourse, new BigDecimal("1.00"), "Grated")
        )), new BigDecimal("9.99"), 500));
        meals.add(new Meal(2L, "Burger", mainCourse, new HashSet<>(Arrays.asList(
                new Ingredient(1L, "Tomato", mainCourse, new BigDecimal("0.50"), "Fresh")
        )), new BigDecimal("7.99"), 700));
        meals.add(new Meal(3L, "Chocolate Cake", desserts, new HashSet<>(Arrays.asList(
                new Ingredient(3L, "Chocolate", desserts, new BigDecimal("2.00"), "Dark")
        )), new BigDecimal("12.99"), 1200));

        Set<Chef> chefs = new HashSet<>();
        chefs.add(new Chef(1L, "John", "Doe", new Contract(new BigDecimal("2000.00"), LocalDate.now(), LocalDate.now().plusMonths(6), ContractType.FULL_TIME), new Bonus(new BigDecimal("100.00"))));
        chefs.add(new Chef(2L, "Alice", "Brown", new Contract(new BigDecimal("2500.00"), LocalDate.now(), LocalDate.now().plusYears(1), ContractType.FULL_TIME), new Bonus(new BigDecimal("200.00"))));

        Set<Waiter> waiters = new HashSet<>();
        waiters.add(new Waiter(1L, "Jane", "Smith", new Contract(new BigDecimal("1500.00"), LocalDate.now(), LocalDate.now().plusMonths(12), ContractType.PART_TIME), new Bonus(new BigDecimal("50.00"))));
        waiters.add(new Waiter(2L, "Tom", "White", new Contract(new BigDecimal("1700.00"), LocalDate.now(), LocalDate.now().plusYears(2), ContractType.PART_TIME), new Bonus(new BigDecimal("75.00"))));

        Set<Deliverer> deliverers = new HashSet<>();
        deliverers.add(new Deliverer(1L, "Mike", "Johnson", new Contract(new BigDecimal("1200.00"), LocalDate.now(), LocalDate.now().plusMonths(6), ContractType.PART_TIME), new Bonus(new BigDecimal("30.00"))));
        deliverers.add(new Deliverer(2L, "Sara", "Davis", new Contract(new BigDecimal("1300.00"), LocalDate.now(), LocalDate.now().plusMonths(8), ContractType.PART_TIME), new Bonus(new BigDecimal("40.00"))));

        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address.BuilderAddress(1L)
                .atStreet("Main Street")
                .atHouseNumber("123")
                .atCity("Zagreb")
                .atPostalCode("10000")
                .build());
        addresses.add(new Address.BuilderAddress(2L)
                .atStreet("Second Avenue")
                .atHouseNumber("456")
                .atCity("Split")
                .atPostalCode("21000")
                .build());

        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(1L, "Restaurant A", addresses.get(0), meals, chefs, waiters, deliverers));
        restaurants.add(new Restaurant(2L, "Restaurant B", addresses.get(1), meals, chefs, waiters, deliverers));


        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1L, restaurants.get(0), new ArrayList<>(meals), deliverers.iterator().next(), LocalDateTime.now()));
        orders.add(new Order(2L, restaurants.get(1), new ArrayList<>(meals), deliverers.iterator().next(), LocalDateTime.now()));

        RestaurantLabourExchangeOffice<Restaurant> restaurantLabourExchangeOffice = new RestaurantLabourExchangeOffice<>(restaurants);
        Map<Meal, RestaurantLabourExchangeOffice<Restaurant>> mealRestaurantMap = mapMealsToRestaurants(restaurantLabourExchangeOffice);

        Scanner scanner = new Scanner(System.in);
        displayRestaurantsForSelectedMeal(scanner, mealRestaurantMap);

        List<Person> employees = new ArrayList<>();
        employees.addAll(chefs);
        employees.addAll(waiters);
        employees.addAll(deliverers);

        Person highestPaidEmployee = findHighestPaidEmployee(employees);
        System.out.println("\nNajplaceniji zaposlenik: ");
        printEmployeeInfo(highestPaidEmployee);

        Person longestContractEmployee = findLongestContractEmployee(employees);
        System.out.println("Zaposlenik sa najdužim ugovorom: ");
        printEmployeeInfo(longestContractEmployee);


        printMealWithMinMaxCalories(specialMeals);

        ComparatorUtils.printHighestPaidEmployeeInRestaurants(restaurantLabourExchangeOffice);

        ComparatorUtils.printHighestEmployeedEmployeeInRestaurants(restaurantLabourExchangeOffice);

        ComparatorUtils.printMealsSortedByRestaurantCount(mealRestaurantMap);

        ComparatorUtils.printSortedIngredientsAlphabetically(meals);

        LambdaUtils.findAndPrintRestaurantWithMostEmployees(restaurants);

        LambdaUtils.findAndPrintMostPopularMeal(mealRestaurantMap);

        LambdaUtils.printIngredientsForAllOrders(orders);

        LambdaUtils.calculateTotalPrice(orders);

        LambdaUtils.groupRestaurantsByCity(restaurantLabourExchangeOffice);

        List<Meal> mealsList = new ArrayList<>(meals);
        List<Meal> highCalorieMeals = LambdaUtils.filterHighCalorieMeals(mealsList);
        List<Meal> sortedMealsAsc = LambdaUtils.sortMealsByCalories(mealsList, true);
        List<Meal> sortedMealsDesc = LambdaUtils.sortMealsByCalories(mealsList, false);

        System.out.println("High Calorie Meals: " + highCalorieMeals);
        System.out.println("Sorted Meals (Asc): " + sortedMealsAsc);
        System.out.println("Sorted Meals (Desc): " + sortedMealsDesc);







    }
}
