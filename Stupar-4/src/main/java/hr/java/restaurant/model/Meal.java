package hr.java.restaurant.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

/**
 * Klasa koja predstavlja obrok u restoranu. Obrok se sastoji od naziva, kategorije, sastojaka, cijene i kalorijske vrijednosti.
 * Ova klasa omogućuje pohranu i manipulaciju informacijama o obrocima koji se nude u restoranu.
 */

public class Meal extends Entity {
    private String name;
    private Category category;
    private Set<Ingredient> ingredient = new HashSet<Ingredient>();
    private BigDecimal price;
    private Integer calories;

    /**
     * Konstruktor koji inicijalizira obrok s potrebnim atributima.
     * @param id jedinstveni identifikator obroka.
     * @param name naziv obroka.
     * @param category kategorija obroka (npr. predjelo, glavno jelo, desert).
     * @param ingredient niz sastojaka koji čine obrok.
     * @param price cijena obroka.
     * @param calories kalorijska vrijednost obroka.
     */

    public Meal(Long id, String name, Category category, Set<Ingredient> ingredient, BigDecimal price, Integer calories) {
        super(id);
        this.name = name;
        this.category = category;
        this.ingredient = ingredient;
        this.price = price;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Set<Ingredient> getIngredient() {
        return ingredient;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setIngredient(SortedSet<Ingredient> ingredient) {
        this.ingredient = ingredient;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Calories: " + calories;
    }
}
