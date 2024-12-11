package hr.java.restaurant.model;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Klasa koja predstavlja salatu kao obrok u restoranu.
 * Ova klasa implementira interfejs {@link Vegan} i proširuje klasu {@link Meal}.
 * Koristi se za upravljanje salatama koje su veganske, sadrže hranjive sastojke i informacije o alergijama.
 */

public final class SaladMeal extends Meal implements Vegan{
    private boolean veganFriendly;

    /**
     * Konstruktor koji inicijalizira salatu s osnovnim informacijama.
     * @param id jedinstveni identifikator obroka
     * @param name ime obroka
     * @param category kategorija obroka (npr. predjelo, glavno jelo)
     * @param ingredients lista sastojaka koji čine salatu
     * @param price cijena obroka
     * @param veganFriendly označava je li salata veganska
     * @param calories broj kalorija u obroku
     */

    public SaladMeal(Long id, String name, Category category, Set<Ingredient> ingredients, BigDecimal price, Boolean veganFriendly, Integer calories) {
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
