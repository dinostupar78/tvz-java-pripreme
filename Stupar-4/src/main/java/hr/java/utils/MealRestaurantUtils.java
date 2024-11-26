package hr.java.utils;

import hr.java.restaurant.enums.PriorityType;
import hr.java.restaurant.model.Meal;
import hr.java.restaurant.model.Order;
import hr.java.restaurant.model.Restaurant;

import java.math.BigDecimal;
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

                List<Restaurant> associatedRestaurants = mealRestaurantMap.getOrDefault(selectedMeal, Collections.emptyList());
                System.out.println("\nRestorani koji nude " + selectedMeal.getName() + ":");

                for (Restaurant restaurant : associatedRestaurants) {
                    System.out.println("- " + restaurant.getName() + " (Adresa: " + restaurant.getAddress().toString() + ")");
                }

            } else {
                System.out.println("Neispravan odabir. Broj mora biti između 1 i " + mealList.size() + ".");
                isValid = false;
            }

        } while(!isValid);
    }

    public static String findCityWithMostRestaurants(List<Restaurant> restaurants) {
        Map<String, Integer> cityRestaurantCount = new HashMap<>();

        // Broji restorane po gradovima
        for (Restaurant restaurant : restaurants) {
            String city = restaurant.getAddress().getCity();
            //cityRestaurantCount.put(city, cityRestaurantCount.containsKey(city)  ? cityRestaurantCount.get(city) + 1 : 1);
            if (cityRestaurantCount.containsKey(city)) {
                cityRestaurantCount.put(city, cityRestaurantCount.get(city) + 1);
            } else {
                cityRestaurantCount.put(city, 1);
            }
        }

        // Pronalazi grad s najviše restorana
        return cityRestaurantCount.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    public static Set<Order> filterOrdersByPriority(Set<Order> orders) {
        // Filtriraj narudžbe veće od 10€
        Set<Order> highValueOrders = new HashSet<>();
        for (Order order : orders) {
            if (order.getTotalPrice().compareTo(BigDecimal.TEN) > 0) {
                highValueOrders.add(order);
            }
        }

        // Pronađi najniži prioritet
        PriorityType lowestPriority = highValueOrders.stream()
                .map(Order::getPriorityType)
                .min(Enum::compareTo)
                .orElse(null);

        // Ukloni narudžbe s najnižim prioritetom
        Iterator<Order> iterator = highValueOrders.iterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            if (order.getPriorityType() == lowestPriority) {
                iterator.remove();
            }
        }

        return highValueOrders;
    }
}
