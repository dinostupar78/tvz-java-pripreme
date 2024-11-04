package hr.java.restaurant.model;

public sealed interface Vegetarian permits PastaMeal {

    boolean isAlDente();

    String getSauceRecommendation();

    boolean isLowPrice();
}
