package hr.java.restaurant.model;

public sealed interface Vegan permits QuinoaSalad {
    boolean isVeganFriendly();

    boolean isGlutenFree();

    boolean isLowPrice();
}
