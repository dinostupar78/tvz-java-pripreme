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

    private final CategoryRepository<Category> categoryRepository = new CategoryRepository<>();
    private final IngredientRepository<Ingredient> ingredientRepository = new IngredientRepository<>(categoryRepository);

    public void initialize() {
        Set<Category> categories = categoryRepository.findAll();

        ingredientComboBoxCategory.setItems(observableArrayList(categories));

        ingredientComboBoxCategory.setConverter(new StringConverter<>() {
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

    public void addNewIngredient() {
        StringBuilder errorMessages = new StringBuilder();

        String ingredientName = ingredientTextFieldName.getText();
        if (ingredientName.isEmpty()) {
            errorMessages.append("Unos naziva sastojka je obavezan!\n");
        }

        Category selectedCategory = ingredientComboBoxCategory.getValue();
        if (selectedCategory == null) {
            errorMessages.append("Odabir kategorije je obavezan!\n");
        }

        String ingredientKcal = ingredientTextFieldKcal.getText();
        BigDecimal kcal = null;
        if (ingredientKcal.isEmpty()) {
            errorMessages.append("Unos kcal sastojka je obavezan!\n");
        } else if (!ingredientKcal.matches("^[0-9]{1,12}(?:\\.[0-9]{1,4})?$")) {
            errorMessages.append("Uneseni kcal sastojka mora biti pozitivna i u formatu, npr. 10.00!\n");
        } else {
            try {
                kcal = new BigDecimal(ingredientKcal);
                if (kcal.compareTo(BigDecimal.ZERO) == 0) {
                    errorMessages.append("Uneseni kcal sastojka ne smije biti 0.00!\n");
                }
            } catch (NumberFormatException e) {
                errorMessages.append("Pogreška pri unosu kcal sastojka. Provjerite format!\n");
            }
        }

        String ingredientPreparationMethod = ingredientTextFieldPreparationMethod.getText();
        if (ingredientPreparationMethod.isEmpty()) {
            errorMessages.append("Unos preparacije sastojka je obavezan!\n");
        }

        if (errorMessages.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pogreške kod unosa novog sastojka");
            alert.setHeaderText("Sastojak nije spremljen zbog pogreški kod unosa");
            alert.setContentText(errorMessages.toString());
            alert.showAndWait();
        } else{

            Ingredient ingredient = new Ingredient(null, ingredientName, selectedCategory, kcal, ingredientPreparationMethod);
            ingredientRepository.save(ingredient);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uspješno spremanje novog sastojka");
            alert.setHeaderText("Sastojak " + ingredientName + " je uspješno spremljen");
            StringBuilder sb = new StringBuilder();
            sb.append("Naziv sastojka: ").append(ingredientName).append("\n");
            sb.append("Kategorija: ").append(selectedCategory.getName()).append("\n");
            sb.append("Broj kalorija: ").append(kcal).append("\n");
            sb.append("Metoda preparacije: ").append(ingredientPreparationMethod).append("\n");
            alert.setContentText(sb.toString());
            alert.showAndWait();

            ingredientTextFieldName.clear();
            ingredientComboBoxCategory.setValue(null);
            ingredientTextFieldKcal.clear();
            ingredientTextFieldPreparationMethod.clear();

        }


    }
}
