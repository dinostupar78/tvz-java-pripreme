package hr.java.restaurant.model;

import java.time.LocalDateTime;

public class Order {
    private Restaurant restaurant;
    private Meal meals;
    private Deliverer deliverers;
    private LocalDateTime deliveryDateAndTime;

    public Order(Restaurant restaurant, Meal meals, Deliverer deliverers, LocalDateTime deliveryDateAndTime) {
        this.restaurant = restaurant;
        this.meals = meals;
        this.deliverers = deliverers;
        this.deliveryDateAndTime = deliveryDateAndTime;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Meal getMeals() {
        return meals;
    }

    public Deliverer getDeliverers() {
        return deliverers;
    }

    public LocalDateTime getDeliveryDateAndTime() {
        return deliveryDateAndTime;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setMeals(Meal meals) {
        this.meals = meals;
    }

    public void setDeliverers(Deliverer deliverers) {
        this.deliverers = deliverers;
    }

    public void setDeliveryDateAndTime(LocalDateTime deliveryDateAndTime) {
        this.deliveryDateAndTime = deliveryDateAndTime;
    }
}
