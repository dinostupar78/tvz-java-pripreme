package hr.java.restaurant.model;

public class Order {
    private Restaurant restaurant;
    private Meal[] meals;
    private Deliverer deliverer;

    public Order(Restaurant restaurant, Meal[] meals, Deliverer deliverer) {
        this.restaurant = restaurant;
        this.meals = meals;
        this.deliverer = deliverer;

    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Meal[] getMeals() {
        return meals;
    }

    public void setMeals(Meal[] meals) {
        this.meals = meals;
    }

    public Deliverer getDeliverer() {
        return deliverer;
    }

    public void setDeliverer(Deliverer deliverer) {
        this.deliverer = deliverer;
    }


}
