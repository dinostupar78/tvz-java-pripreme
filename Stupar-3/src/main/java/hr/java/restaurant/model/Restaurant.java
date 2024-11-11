package hr.java.restaurant.model;

/**
 * Klasa koja predstavlja restoran u sustavu.
 * Sadrži informacije o restoranu, uključujući ime, adresu, dostupne obroke, kuhare, konobare i dostavljače.
 * Ova klasa omogućuje upravljanje restoranom i povezivanje s različitim osobama i obrocima.
 */

public class Restaurant extends Entity {
    private String name;
    private Address address;
    private Meal[] meals;
    private Chef[] chefs;
    private Waiter[] waiters;
    private Deliverer[] deliverers;

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

    public Restaurant(Long id, String name, Address address, Meal[] meals, Chef[] chefs, Waiter[] waiters, Deliverer[] deliverers) {
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

    public Meal[] getMeals() {
        return meals;
    }

    public void setMeals(Meal[] meals) {
        this.meals = meals;
    }

    public Chef[] getChefs() {
        return chefs;
    }

    public void setChefs(Chef[] chefs) {
        this.chefs = chefs;
    }

    public Waiter[] getWaiters() {
        return waiters;
    }

    public void setWaiters(Waiter[] waiters) {
        this.waiters = waiters;
    }

    public Deliverer[] getDeliverers() {
        return deliverers;
    }

    public void setDeliverers(Deliverer[] deliverers) {
        this.deliverers = deliverers;
    }
}
