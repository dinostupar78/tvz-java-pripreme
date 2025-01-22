package hr.javafx.restaurant.generics;

import hr.javafx.restaurant.model.Restaurant;

import java.util.List;

public class RestaurantLabourExchangeOffice <T extends Restaurant>{
    private List<T> restaurants;

    public RestaurantLabourExchangeOffice(List<T> restaurants) {
        this.restaurants = restaurants;
    }

    public List<T> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<T> restaurants) {
        this.restaurants = restaurants;
    }

    public void addRestaurant(T restaurant) {
        restaurants.add(restaurant);
    }

    public boolean removeRestaurant(T restaurant) {
        return restaurants.remove(restaurant);
    }
}
