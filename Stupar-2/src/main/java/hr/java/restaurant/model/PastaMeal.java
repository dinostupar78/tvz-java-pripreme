package hr.java.restaurant.model;

import java.math.BigDecimal;

public final class PastaMeal extends Meal implements Vegetarian {
    private Boolean vegetarianFriendly;

    public PastaMeal(Long id, String name, Category category, Ingredient[] ingredients, BigDecimal price, Boolean vegetarianFriendly, Integer calories) {
        super(id, name, category, ingredients, price, calories);
        this.vegetarianFriendly = vegetarianFriendly;
    }

    public Boolean getVegetarianFriendly() {
        return vegetarianFriendly;
    }

    public void setVegetarianFriendly(Boolean vegetarianFriendly) {
        this.vegetarianFriendly = vegetarianFriendly;
    }

    @Override
    public boolean isAlDente() {
        return true;
    }

    @Override
    public String getSauceRecommendation() {
        return "Preporučeni umak: Marinara s svježim bosiljkom.";
    }

    @Override
    public boolean isLowPrice() {
        return getPrice().compareTo(BigDecimal.valueOf(500)) < 0;
    }

}
