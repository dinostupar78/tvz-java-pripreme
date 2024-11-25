package hr.java.utils;

import hr.java.restaurant.model.Meal;
import hr.java.restaurant.model.Restaurant;

import java.util.*;

public class MealRestaurantUtils {
    public static Map<Meal, List<Restaurant>> mapMealsToRestaurants(List<Restaurant> restaurants) {
        Map<Meal, List<Restaurant>> mealRestaurantMap = new HashMap<>();

        for(Restaurant restaurant : restaurants){
            for(Meal meal : restaurant.getMeals()){
                if (!mealRestaurantMap.containsKey(meal)) {
                    mealRestaurantMap.put(meal, new ArrayList<>());
                }
                mealRestaurantMap.get(meal).add(restaurant);
            }

        }
        return mealRestaurantMap;
    }

    public static void displayRestaurantsForSelectedMeal(Scanner scanner, Map<Meal, List<Restaurant>> mealRestaurantMap) {
        System.out.println("\nOdaberite jedno od jela za prikaz restorana koji ga nude:");

        // Display available meals to the user
        List<Meal> mealList = new ArrayList<>(mealRestaurantMap.keySet());
        for (int i = 0; i < mealList.size(); i++) {
            System.out.println((i + 1) + ". " + mealList.get(i).getName());
        }

        System.out.print("Unesite broj odabranog jela: ");
        int selectedIndex = scanner.nextInt() - 1;

        if (selectedIndex >= 0 && selectedIndex < mealList.size()) {
            Meal selectedMeal = mealList.get(selectedIndex);

            // Fetch and display the restaurants for the selected meal
            List<Restaurant> associatedRestaurants = mealRestaurantMap.getOrDefault(selectedMeal, Collections.emptyList());
            System.out.println("\nRestorani koji nude " + selectedMeal.getName() + ":");

            if (!associatedRestaurants.isEmpty()) {
                for (Restaurant restaurant : associatedRestaurants) {
                    System.out.println("- " + restaurant.getName() + " (Adresa: " + restaurant.getAddress().toString() + ")");
                }
            } else {
                System.out.println("Nema restorana koji nude ovo jelo.");
            }
        } else {
            System.out.println("Neispravan odabir. Pokušajte ponovo.");
        }
    }
}
