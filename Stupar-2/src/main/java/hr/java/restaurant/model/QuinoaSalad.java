package hr.java.restaurant.model;

import java.math.BigDecimal;

public final class QuinoaSalad extends Meal implements Vegan{
    private boolean veganFriendly;

    public QuinoaSalad(Long id, String name, Category category, Ingredient[] ingredients, BigDecimal price, Boolean veganFriendly) {
        super(id, name, category, ingredients, price);
        this.veganFriendly = veganFriendly;
    }

    public boolean isVeganFriendly() {
        return veganFriendly;
    }

    public void setVeganFriendly(boolean veganFriendly) {
        this.veganFriendly = veganFriendly;
    }

    @Override
    public boolean isGlutenFree() {
        String[] glutenContainingIngredients = {"wheat", "barley", "rye"};

        // Provjera svih sastojaka salate
        for (Ingredient ingredient : getIngredient()) {
            // Provjera da li sastojak sadrži gluten
            for (String glutenIngredient : glutenContainingIngredients) {
                if (ingredient.equals(glutenIngredient)) {
                    return false; // Ako se pronađe gluten, jelo nije bezglutensko
                }
            }
        }
        return true; // Ako nema glutena, vraća true
    }

    @Override
    public boolean isLowPrice() {
        return getPrice().compareTo(BigDecimal.valueOf(300)) < 0;
    }

}
