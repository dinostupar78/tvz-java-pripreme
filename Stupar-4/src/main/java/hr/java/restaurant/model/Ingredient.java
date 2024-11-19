package hr.java.restaurant.model;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Klasa koja predstavlja sastojak u restoranu. Sastojak se definira svojim nazivom, kategorijom, kalorijama i metodom pripreme.
 * Ova klasa omogućuje pohranu i manipulaciju informacijama o sastojcima koji se koriste u jelima.
 */

public class Ingredient extends Entity{
    private String name;
    private Category category;
    private BigDecimal kcal;
    private String preparationMethod;

    /**
     * Konstruktor koji inicijalizira sastojak s potrebnim atributima.
     * @param id jedinstveni identifikator sastojka.
     * @param name naziv sastojka.
     * @param category kategorija u kojoj sastojak pripada (npr. povrće, meso, itd.).
     * @param kcal kalorijska vrijednost sastojka.
     * @param preparationMethod metoda pripreme sastojka (npr. pečeno, kuhano).
     */

    public Ingredient(Long id, String name, Category category, BigDecimal kcal, String preparationMethod) {
        super(id);
        this.name = name;
        this.category = category;
        this.kcal = kcal;
        this.preparationMethod = preparationMethod;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public BigDecimal getKcal() {
        return kcal;
    }

    public String getPreparationMethod() {
        return preparationMethod;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setKcal(BigDecimal kcal) {
        this.kcal = kcal;
    }

    public void setPreparationMethod(String preparationMethod) {
        this.preparationMethod = preparationMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(name, that.name) && Objects.equals(category, that.category) && Objects.equals(kcal, that.kcal) && Objects.equals(preparationMethod, that.preparationMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, kcal, preparationMethod);
    }
}
