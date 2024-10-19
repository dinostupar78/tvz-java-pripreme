package hr.java.restaurant.model;

import java.math.BigDecimal;

public class Meal {
    private String name;
    private Category category;
    private Ingredient ingredient;
    private BigDecimal price;

    public Meal(String name, Category category, Ingredient ingredient, BigDecimal price) {
        this.name = name;
        this.category = category;
        this.ingredient = ingredient;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Ingredient getIngredient() {
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

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
