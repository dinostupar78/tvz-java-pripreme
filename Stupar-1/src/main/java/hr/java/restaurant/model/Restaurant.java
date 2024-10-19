package hr.java.restaurant.model;

public class Restaurant {
    private String name;
    private Address address;
    private Meal meals;
    private Chef chefs;
    private Waiter waiters;
    private Deliverer deliverers;

    public Restaurant(String name, Address address, Meal meals, Chef chefs, Waiter waiters, Deliverer deliverers) {
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

    public Address getAddress() {
        return address;
    }

    public Meal getMeals() {
        return meals;
    }

    public Chef getChefs() {
        return chefs;
    }

    public Waiter getWaiters() {
        return waiters;
    }

    public Deliverer getDeliverers() {
        return deliverers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setMeals(Meal meals) {
        this.meals = meals;
    }

    public void setChefs(Chef chefs) {
        this.chefs = chefs;
    }

    public void setWaiters(Waiter waiters) {
        this.waiters = waiters;
    }

    public void setDeliverers(Deliverer deliverers) {
        this.deliverers = deliverers;
    }
}
