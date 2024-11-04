package hr.java.restaurant.model;

import java.math.BigDecimal;

public final class BeefMeal extends Meal implements Meat{
    private Boolean MeatFrendly;

    public BeefMeal(Long id, String name, Category category, Ingredient[] ingredients, BigDecimal price, Boolean isMeatFrendly, Integer calories) {
        super(id, name, category, ingredients, price, calories);
        this.MeatFrendly = isMeatFrendly;
    }

    public Boolean getMeatFrendly() {
        return MeatFrendly;
    }

    public void setMeatFrendly(Boolean meatFrendly) {
        MeatFrendly = meatFrendly;
    }

    @Override
    public String getRecommendedSideDish() {
        return "Preporučeni prilog: Pire krumpir s grillanim povrćem..";
    }

    @Override
    public boolean isWellDone() {
        return true;
    }

    @Override
    public boolean isLowPrice() {
        return getPrice().compareTo(BigDecimal.valueOf(300)) < 0;
    }

}
