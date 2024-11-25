package hr.java.restaurant.sort;

import hr.java.restaurant.model.Meal;
import hr.java.restaurant.model.Restaurant;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MealRestaurantCountComparator implements Comparator<Meal> {
    public final Map<Meal, List<Restaurant>> mealRestaurantMap;

    public MealRestaurantCountComparator(Map<Meal, List<Restaurant>> mealRestaurantMap) {
        this.mealRestaurantMap = mealRestaurantMap;
    }

    @Override
    public int compare(Meal m1, Meal m2) {
        Integer count1 = mealRestaurantMap.containsKey(m1) ? mealRestaurantMap.get(m1).size() : 0;
        Integer count2 = mealRestaurantMap.containsKey(m2) ? mealRestaurantMap.get(m2).size() : 0;

        return Integer.compare(count2, count1);
    }
}
