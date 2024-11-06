package hr.java.restaurant.model;

public sealed interface Vegan permits SaladMeal {

    String displayDressing();

    boolean containsAllergens();

    boolean isLowPrice();
}
