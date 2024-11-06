package hr.java.restaurant.model;

import java.math.BigDecimal;

public final class SaladMeal extends Meal implements Vegan{
    private boolean veganFriendly;

    public SaladMeal(Long id, String name, Category category, Ingredient[] ingredients, BigDecimal price, Boolean veganFriendly, Integer calories) {
        super(id, name, category, ingredients, price, calories);
        this.veganFriendly = veganFriendly;
    }

    public boolean getVeganFriendly() {
        return veganFriendly;
    }

    public void setVeganFriendly(boolean veganFriendly) {
        this.veganFriendly = veganFriendly;
    }

    @Override
    public String displayDressing() {
        return "Ova salata sadrži svježe povrće, hrskave orašaste plodove i lagani preliv.";
    }

    @Override
    public boolean containsAllergens() {
        return false;
    }

    @Override
    public boolean isLowPrice() {
        return getPrice().compareTo(BigDecimal.valueOf(300)) < 0;
    }

}
