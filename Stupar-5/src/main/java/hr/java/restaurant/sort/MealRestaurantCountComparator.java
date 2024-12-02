package hr.java.restaurant.sort;

import hr.java.restaurant.generics.RestaurantLabourExchangeOffice;
import hr.java.restaurant.model.Meal;
import hr.java.restaurant.model.Restaurant;

import java.util.Comparator;
import java.util.Map;

/**
 * Klasa koja implementira sučelje {@link Comparator} za usporedbu objekata klase {@link Meal} prema broju restorana u kojima je moguće naručiti određeno jelo
 * Usporedba se vrši na temelju broja restorana vezanih za svako jelo. Jela s većim brojem restorana bit će veća u sortiranoj listi
 * @see Meal
 * @see Restaurant
 */

public class MealRestaurantCountComparator implements Comparator<Meal> {
    public final Map<Meal, RestaurantLabourExchangeOffice<Restaurant>> mealRestaurantMap;

    public MealRestaurantCountComparator(Map<Meal, RestaurantLabourExchangeOffice<Restaurant>> mealRestaurantMap) {
        this.mealRestaurantMap = mealRestaurantMap;
    }

    @Override
    public int compare(Meal m1, Meal m2) {
        int count1 = mealRestaurantMap.containsKey(m1) ? mealRestaurantMap.get(m1).getRestaurants().size() : 0;
        int count2 = mealRestaurantMap.containsKey(m2) ? mealRestaurantMap.get(m2).getRestaurants().size() : 0;

        return Integer.compare(count2, count1);
    }
}
