package hr.java.utils;

import hr.java.restaurant.generics.RestaurantLabourExchangeOffice;
import hr.java.restaurant.model.Meal;
import hr.java.restaurant.model.Order;
import hr.java.restaurant.model.Restaurant;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LambdaUtils {


    public static void findAndPrintRestaurantWithMostEmployees(List<Restaurant> restaurants){
        Optional<Restaurant> restaurantWithMostEmployees = restaurants.stream()
                .max((r1, r2) -> Integer.compare(r1.getEmployees().size(), r2.getEmployees().size()));

        restaurantWithMostEmployees.ifPresentOrElse(
                restaurant -> {
                    System.out.println("\nRestoran s najviše zaposlenih:");
                    System.out.println("Ime: " + restaurant.getName());
                    System.out.println("Broj zaposlenih: " + restaurant.getEmployees().size());
                },
                () -> System.out.println("Nema dostupnih restorana.")
        );
    }

    public static void findAndPrintMostPopularMeal(Map<Meal, RestaurantLabourExchangeOffice<Restaurant>> mealRestaurantMap){
        mealRestaurantMap.entrySet().stream()
                .max(Comparator.comparingInt(entry -> entry.getValue().getRestaurants().size())) // Usporedba broja restorana
                .ifPresent(mealEntry -> {
                    Meal mostPopularMeal = mealEntry.getKey();
                    int restaurantCount = mealEntry.getValue().getRestaurants().size();

                    System.out.println("\nNajčešće naručivano jelo:");
                    System.out.println("Naziv: " + mostPopularMeal.getName());
                    System.out.println("Broj restorana: " + restaurantCount);
                    System.out.println("Kategorija: " + mostPopularMeal.getCategory().getName());
                    System.out.println("Cijena: " + mostPopularMeal.getPrice() + " EUR");
                });

    }

    public static void printIngredientsForAllOrders(List<Order> orders){
        orders.stream()
                .flatMap(order -> order.getMeals().stream())
                .flatMap(meal -> meal.getIngredient().stream())
                .forEach(ingredient -> System.out.println("\nNamirinica: " + ingredient.getName()));
    }

    public static void calculateTotalPrice(List<Order> orders){
        BigDecimal totalPrice = orders.stream()
                .flatMap(order -> order.getMeals().stream())
                .map(meal -> meal.getPrice())
                .reduce(BigDecimal.ZERO, (x, y) -> x.add(y)); // ili BigDecimal::add

        System.out.println("\nUkupna cijena: " + totalPrice);

    }

    public static Map<String, RestaurantLabourExchangeOffice<Restaurant>> groupRestaurantsByCity(RestaurantLabourExchangeOffice<Restaurant> genericRestaurantList){
        return genericRestaurantList.getRestaurants().stream()
                .collect(Collectors.groupingBy(
                        restaurant -> restaurant.getAddress().getCity(),
                        Collectors.collectingAndThen(Collectors.toList(), restaurants -> new RestaurantLabourExchangeOffice<>(restaurants))
                ));

    }


}
