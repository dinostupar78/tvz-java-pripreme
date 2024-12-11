package hr.java.restaurant.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa koja predstavlja narudžbu u restoranu.
 * Svaka narudžba sadrži informacije o restoranu, jelu, dostavljaču i vremenu isporuke.
 * Također omogućuje izračun ukupne cijene narudžbe temeljem cijena pojedinačnih jela.
 */

public class Order extends Entity implements Serializable {
    private Restaurant restaurant;
    private List<Meal> meals = new ArrayList<>();
    private Deliverer deliverer;
    private LocalDateTime deliveryDateAndTime;

    /**
     * Konstruktor koji inicijalizira novu narudžbu.
     * @param id jedinstveni identifikator narudžbe
     * @param restaurant restoran u kojem je narudžba napravljena
     * @param meals niz jela koja su naručena
     * @param deliverer dostavljač koji preuzima i isporučuje narudžbu
     * @param deliveryDateAndTime datum i vrijeme isporuke
     */

    public Order(Long id, Restaurant restaurant, List<Meal> meals, Deliverer deliverer, LocalDateTime deliveryDateAndTime) {
        super(id);
        this.restaurant = restaurant;
        this.meals = meals;
        this.deliverer = deliverer;
        this.deliveryDateAndTime = deliveryDateAndTime;
    }

    public LocalDateTime getDeliveryDateAndTime() {
        return deliveryDateAndTime;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public Deliverer getDeliverer() {
        return deliverer;
    }

    public void setDeliverer(Deliverer deliverer) {
        this.deliverer = deliverer;
    }

    public void setDeliveryDateAndTime(LocalDateTime deliveryDateAndTime) {
        this.deliveryDateAndTime = deliveryDateAndTime;
    }

    /**
     * Izračunava ukupnu cijenu narudžbe zbrajanjem cijena svih jela u narudžbi.
     * @return ukupna cijena svih jela u narudžbi
     */

    public BigDecimal getTotalPrice() {
        BigDecimal total = BigDecimal.ZERO; // Initialize total as zero
        for (Meal meal : meals) {
            if (meal != null) {
                total = total.add(meal.getPrice()); // Add meal price
            }
        }
        return total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "meals=" + meals +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }


}
