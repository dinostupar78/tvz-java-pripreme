package hr.javafx.controller;

import hr.javafx.restaurant.model.Category;
import hr.javafx.restaurant.model.Ingredient;
import hr.javafx.restaurant.repository.CategoryRepository;
import hr.javafx.restaurant.repository.IngredientRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.util.Set;

import static javafx.collections.FXCollections.observableArrayList;

public class IngredientAddController {
    @FXML
    private TextField ingredientTextFieldName;

    @FXML
    private ComboBox<Category> ingredientComboBoxCategory;

    @FXML
    private TextField ingredientTextFieldKcal;

    @FXML
    private TextField ingredientTextFieldPreparationMethod;

    CategoryRepository<Category> categoryRepository = new CategoryRepository<>();
    IngredientRepository<Ingredient> ingredientRepository = new IngredientRepository<>(categoryRepository);


    public void initialize(){
        Set<Category> categories = categoryRepository.findAll();

        ingredientComboBoxCategory.setItems(observableArrayList(categories));

        ingredientComboBoxCategory.setConverter(new StringConverter<Category>() {

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

    public void addNewIngredient(){
        String ingredientName = ingredientTextFieldName.getText();
        Category selectedCategory = ingredientComboBoxCategory.getValue();

        String ingredientKcal = ingredientTextFieldKcal.getText();
        BigDecimal kcal = new BigDecimal(ingredientKcal);

        String ingredientPreparationMethod = ingredientTextFieldPreparationMethod.getText();

        Ingredient ingredient = new Ingredient(null, ingredientName, selectedCategory, kcal, ingredientPreparationMethod);
        ingredientRepository.save(ingredient);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uspješno spremanje nove kategorije");
        alert.setHeaderText("Sastojak " + ingredientName + " je uspješno spremljen");
        StringBuilder sb = new StringBuilder();
        sb.append("Ime kategorije: ")
                .append(selectedCategory)
                .append("\n");
        sb.append("Broj kalorija: ")
                .append(ingredientKcal)
                .append("\n");
        sb.append("Metoda preparacije: ")
                .append(ingredientPreparationMethod)
                .append("\n");
        alert.setContentText(sb.toString());
        alert.showAndWait();

        ingredientTextFieldName.clear();
        ingredientComboBoxCategory.setValue(null);
        ingredientTextFieldKcal.clear();
        ingredientTextFieldPreparationMethod.clear();




    }
}
