package hr.java.restaurant.model;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Klasa koja predstavlja jelo od tjestenine u restoranu.
 * Ova klasa proširuje {@link Meal} i implementira {@link Vegetarian} sučelje,
 * dodajući mogućnost označavanja jela kao vegetarijanskog, te nudi specifične metode
 * za preporučeni umak, al dente tip tjestenine i provjeru cijene.
 */

public final class PastaMeal extends Meal implements Vegetarian {
    private Boolean vegetarianFriendly;

    /**
     * Konstruktor koji inicijalizira novo jelo od tjestenine.
     * @param id jedinstveni identifikator jela
     * @param name naziv jela
     * @param category kategorija jela
     * @param ingredients niz sastojaka koji čine jelo
     * @param price cijena jela
     * @param vegetarianFriendly označava je li jelo vegetarijansko
     * @param calories broj kalorija u jelu
     */

    public PastaMeal(Long id, String name, Category category, Set<Ingredient> ingredients, BigDecimal price, Boolean vegetarianFriendly, Integer calories) {
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
