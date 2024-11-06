package hr.java.restaurant.model;

public sealed interface Meat permits BeefMeal {

    String getRecommendedSideDish();

    boolean isWellDone();

    boolean isLowPrice();
}
