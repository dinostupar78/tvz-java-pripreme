package hr.java.restaurant.model;

import java.math.BigDecimal;

public class Meal extends Entity {
    private String name;
    private Category category;
    private Ingredient[] ingredient;
    private BigDecimal price;
    private Integer calories;

    public Meal(Long id, String name, Category category, Ingredient[] ingredient, BigDecimal price, Integer calories) {
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

    public Ingredient[] getIngredient() {
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

    public void setIngredient(Ingredient[] ingredient) {
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
