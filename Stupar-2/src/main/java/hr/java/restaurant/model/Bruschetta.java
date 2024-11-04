package hr.java.restaurant.model;

import java.math.BigDecimal;

public final class Bruschetta extends Meal implements Vegetarian {
    private Boolean vegetarianFriendly;

    public Bruschetta(Long id, String name, Category category, Ingredient[] ingredients, BigDecimal price, Boolean vegetarianFriendly) {
        super(id, name, category, ingredients, price);
        this.vegetarianFriendly = vegetarianFriendly;
    }

    public Boolean isVegetarianFriendly() {
        return vegetarianFriendly;
    }

    public void setVegetarianFriendly(Boolean vegetarianFriendly) {
        this.vegetarianFriendly = vegetarianFriendly;
    }

    @Override
    public boolean isLowPrice() {
        return getPrice().compareTo(BigDecimal.valueOf(400)) < 0;
    }


}
