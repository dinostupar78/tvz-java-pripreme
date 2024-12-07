package hr.java.utils;
import hr.java.restaurant.generics.RestaurantLabourExchangeOffice;
import hr.java.restaurant.model.Meal;
import hr.java.restaurant.model.Restaurant;
import java.util.*;

public class MealRestaurantUtils {
    public static Map<Meal, RestaurantLabourExchangeOffice<Restaurant>> mapMealsToRestaurants(RestaurantLabourExchangeOffice<Restaurant> genericRestaurantList) {
        Map<Meal, RestaurantLabourExchangeOffice<Restaurant>> mealRestaurantMap = new HashMap<>();

        for(Restaurant restaurant : genericRestaurantList.getRestaurants()){
            for(Meal meal : restaurant.getMeals()){
                if (!mealRestaurantMap.containsKey(meal)) {
                    mealRestaurantMap.put(meal, new RestaurantLabourExchangeOffice<>(new ArrayList<>()));
                }
                mealRestaurantMap.get(meal).getRestaurants().add(restaurant);
            }

        }
        return mealRestaurantMap;
    }

    public static Map<String, RestaurantLabourExchangeOffice<Restaurant>> mapCitiesToRestaurants(RestaurantLabourExchangeOffice<Restaurant> genericRestaurantList) {
        Map<String, RestaurantLabourExchangeOffice<Restaurant>> mealRestaurantCity = new HashMap<>();

        for (Restaurant restaurant : genericRestaurantList.getRestaurants()) {
            String city = restaurant.getAddress().getCity();

            if (!mealRestaurantCity.containsKey(city)) {
                mealRestaurantCity.put(city, new RestaurantLabourExchangeOffice<>(new ArrayList<>()));
            }

            mealRestaurantCity.get(city).getRestaurants().add(restaurant);
        }

        return mealRestaurantCity;
    }

    public static void displayRestaurantsForSelectedMeal(Scanner scanner, Map<Meal, RestaurantLabourExchangeOffice<Restaurant>> mealRestaurantMap) {
        Boolean isValid;
        System.out.println("\nOdaberite jedno od jela za prikaz restorana koji ga nude:");
        List<Meal> mealList = new ArrayList<>(mealRestaurantMap.keySet());
        for (int i = 0; i < mealList.size(); i++) {
            System.out.println((i + 1) + ". " + mealList.get(i).getName());
        }

        do{
            isValid = true;
            System.out.print("Unesite broj odabranog jela: ");
            int selectedIndex = scanner.nextInt() - 1;
            if (selectedIndex >= 0 && selectedIndex < mealList.size()) {
                Meal selectedMeal = mealList.get(selectedIndex);

                RestaurantLabourExchangeOffice<Restaurant> associatedRestaurants = mealRestaurantMap.getOrDefault(selectedMeal, new RestaurantLabourExchangeOffice<>(new ArrayList<>()));
                System.out.println("\nRestorani koji nude " + selectedMeal.getName() + ":");

                for (Restaurant restaurant : associatedRestaurants.getRestaurants()) {
                    System.out.println("- " + restaurant.getName() + " (Adresa: " + restaurant.getAddress().toString() + ")");
                }

            } else {
                System.out.println("Neispravan odabir. Broj mora biti između 1 i " + mealList.size() + ".");
                isValid = false;
            }

        } while(!isValid);
    }
}
