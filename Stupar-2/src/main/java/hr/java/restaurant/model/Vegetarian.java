package hr.java.restaurant.model;

public sealed interface Vegetarian permits Bruschetta {

    Boolean isVegetarianFriendly();

    boolean isLowPrice();
}
