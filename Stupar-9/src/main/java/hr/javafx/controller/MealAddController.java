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
        StringBuilder errorMessages = new StringBuilder();

        String mealName = mealTextFieldName.getText();
        if(mealName.isEmpty()){
            errorMessages.append("Unos imena jela je obavezan!\n");
        }
        Category selectedCategory = mealComboBoxCategory.getValue();
        if (selectedCategory == null) {
            errorMessages.append("Odabir kategorije je obavezan!\n");
        }

        Set<Ingredient> ingredients = ingredientRepository.findAll();

        String mealPrice = mealTextFieldPrice.getText();
        BigDecimal price = null;
        if (mealPrice.isEmpty()) {
            errorMessages.append("Unos cijene jela je obavezan!\n");
        } else if (!mealPrice.matches("^[0-9]{1,12}(?:\\.[0-9]{1,4})?$")) {
            errorMessages.append("Unos cijene jela mora biti pozitivan i u formatu, npr. 10.00!\n");
        } else {
            try {
                price = new BigDecimal(mealPrice);
                if (price.compareTo(BigDecimal.ZERO) == 0) {
                    errorMessages.append("Unesena cijena jela ne smije biti 0.00!\n");
                }
            } catch (NumberFormatException e) {
                errorMessages.append("Pogreška pri unosu cijene jela. Provjerite format!\n");
            }
        }

        String mealCalories = mealTextFieldCalories.getText();
        Integer calories = null;
        if (mealCalories.isEmpty()) {
            errorMessages.append("Unos kalorija je obavezan!\n");
        } else {
            try {
                calories = Integer.valueOf(mealCalories);
                if (calories <= 0) {
                    errorMessages.append("Broj kalorija mora biti pozitivan broj!\n");
                }
            } catch (NumberFormatException e) {
                errorMessages.append("Pogreška pri unosu kalorija. Unesite cijeli broj!\n");
            }
        }

        if (errorMessages.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pogreške kod unosa novog jela");
            alert.setHeaderText("Jelo nije spremljeno zbog pogreški kod unosa");
            alert.setContentText(errorMessages.toString());
            alert.showAndWait();
        } else{
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

}
