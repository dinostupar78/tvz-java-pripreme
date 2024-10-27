package hr.java.restaurant.model;

import java.math.BigDecimal;

public class Ingredient {
    private String name;
    private Category category;
    private BigDecimal kcal;
    private String preparationMethod;

    public Ingredient(String name, Category category, BigDecimal kcal, String preparationMethod) {
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
}
