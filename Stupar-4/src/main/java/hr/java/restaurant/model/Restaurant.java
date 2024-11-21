package hr.java.restaurant.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Klasa koja predstavlja restoran u sustavu.
 * Sadrži informacije o restoranu, uključujući ime, adresu, dostupne obroke, kuhare, konobare i dostavljače.
 * Ova klasa omogućuje upravljanje restoranom i povezivanje s različitim osobama i obrocima.
 */

public class Restaurant extends Entity {
    private String name;
    private Address address;
    private Set<Meal> meals = new HashSet<>();
    private Set<Chef> chefs = new HashSet<>();
    private Set<Waiter> waiters = new HashSet<>();;
    private Set<Deliverer> deliverers;

    /**
     * Konstruktor koji inicijalizira restoran s osnovnim informacijama.
     * @param id jedinstveni identifikator restorana
     * @param name ime restorana
     * @param address adresa restorana
     * @param meals lista obroka koji su dostupni u restoranu
     * @param chefs lista kuhara koji rade u restoranu
     * @param waiters lista konobara koji rade u restoranu
     * @param deliverers lista dostavljača koji rade za restoran
     */

    public Restaurant(Long id, String name, Address address, Set<Meal> meals, Set<Chef> chefs, Set<Waiter> waiters, Set<Deliverer> deliverers) {
        super(id);
        this.name = name;
        this.address = address;
        this.meals = meals;
        this.chefs = chefs;
        this.waiters = waiters;
        this.deliverers = deliverers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }

    public Set<Chef> getChefs() {
        return chefs;
    }

    public void setChefs(Set<Chef> chefs) {
        this.chefs = chefs;
    }

    public Set<Waiter> getWaiters() {
        return waiters;
    }

    public void setWaiters(Set<Waiter> waiters) {
        this.waiters = waiters;
    }

    public Set<Deliverer> getDeliverers() {
        return deliverers;
    }

    public void setDeliverers(Set<Deliverer> deliverers) {
        this.deliverers = deliverers;
    }
}
