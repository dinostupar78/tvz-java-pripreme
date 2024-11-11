package hr.java.restaurant.model;
import java.math.BigDecimal;

/**
 * Predstavlja obrok na bazi govedine.
 * Klasa proširuje klasu {@link Meal} i implementira {@link Meat} sučelje.
 * Osim osnovnih informacija o obroku kao što su naziv, kategorija, sastojci, cijena i kalorije,
 * ova klasa sadrži specifične informacije vezane za obrok od govedine, kao što je da li je obrok "meat friendly".
 * Također pruža specifične implementacije za preporučeni prilog, način pripreme (well done) i cijenu.
 */

public final class BeefMeal extends Meal implements Meat{
    private Boolean MeatFrendly;

    /**
     * Konstruktor za stvaranje objekta BeefMeal.
     * @param id Jedinstveni identifikator obroka.
     * @param name Naziv obroka.
     * @param category Kategorija obroka.
     * @param ingredients Sastojci obroka.
     * @param price Cijena obroka.
     * @param isMeatFrendly Da li je obrok "meat friendly".
     * @param calories Broj kalorija u obroku.
     */

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
