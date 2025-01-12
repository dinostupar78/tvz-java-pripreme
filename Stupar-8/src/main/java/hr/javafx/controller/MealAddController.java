package hr.javafx.controller;

import hr.javafx.restaurant.model.Category;
import hr.javafx.restaurant.model.Ingredient;
import hr.javafx.restaurant.model.Meal;
import hr.javafx.restaurant.repository.CategoryRepository;
import hr.javafx.restaurant.repository.IngredientRepository;
import hr.javafx.restaurant.repository.MealsRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.util.Set;

import static javafx.collections.FXCollections.observableArrayList;

public class MealAddController {
    @FXML
    private TextField mealTextFieldName;

    @FXML
    private ComboBox<Category> mealComboBoxCategory;

    @FXML
    private TextField mealTextFieldPrice;

    @FXML
    private TextField mealTextFieldCalories;

    CategoryRepository<Category> categoryRepository = new CategoryRepository<>();
    IngredientRepository<Ingredient> ingredientRepository = new IngredientRepository<>(categoryRepository);
    MealsRepository<Meal> mealRepository = new MealsRepository<>(categoryRepository);

    public void initialize(){
        Set<Category> categories = categoryRepository.findAll();

        mealComboBoxCategory.setItems(observableArrayList(categories));

        mealComboBoxCategory.setConverter(new StringConverter<Category>() {

            @Override
            public String toString(Category category) {
                return category != null ? category.getName() : "";
            }

            @Override
            public Category fromString(String s) {
                return null;
            }
        });
    }

    public void addNewMeal() {
        String mealName = mealTextFieldName.getText();
        Category selectedCategory = mealComboBoxCategory.getValue();

        Set<Ingredient> ingredients = ingredientRepository.findAll();

        String mealPrice = mealTextFieldPrice.getText();
        BigDecimal price = new BigDecimal(mealPrice);

        String mealCalories = mealTextFieldCalories.getText();
        Integer calories = Integer.valueOf(mealCalories);

        Meal meal = new Meal(null, mealName, selectedCategory, ingredients, price, calories);
        mealRepository.save(meal);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uspješno spremanje novog jela");
        alert.setHeaderText("Jelo " + mealName + " je uspješno spremljen");
        StringBuilder sb = new StringBuilder();
        sb.append("Ime kategorije: ")
                .append(selectedCategory)
                .append("\n");
        sb.append("Cijena: ")
                .append(price)
                .append("\n");
        sb.append("Kalorije: ")
                .append(calories)
                .append("\n");
        alert.setContentText(sb.toString());
        alert.showAndWait();

        mealTextFieldName.clear();
        mealComboBoxCategory.setValue(null);
        mealTextFieldPrice.clear();
        mealTextFieldCalories.clear();

    }



}
