package hr.javafx.restaurant.sort;

import hr.javafx.restaurant.model.Ingredient;

import java.util.Comparator;

/**
 * Klasa koja implementira sučelje {@link Comparator} za usporedbu objekata klase {@link Ingredient} prema abecednom redoslijedu na temelju naziva namirnica
 * @see Ingredient
 */

public class IngredientAlphabeticalComparator implements Comparator <Ingredient> {
    @Override
    public int compare(Ingredient i1, Ingredient i2) {
        return i1.getName().compareTo(i2.getName());
    }
}
