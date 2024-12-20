package hr.javafx.controller;

import hr.javafx.restaurant.model.Category;
import hr.javafx.restaurant.model.Ingredient;
import hr.javafx.restaurant.model.Meal;
import hr.javafx.utils.HandleSearchClickUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class SomethingController {
    public void onSearchSomethingClick(ActionEvent event){
        HandleSearchClickUtils searchClickUtils = new HandleSearchClickUtils();
        searchClickUtils.handleSearchClickSomething(event);
    }

    @FXML
    private TableView<Meal> mealTable;
    @FXML
    private TableColumn<Meal, String> mealNameColumn;
    @FXML
    private TableColumn<Meal, String> mealCategoryColumn;
    @FXML
    private TextField mealFilterField;

    @FXML
    private TableView<Ingredient> ingredientTable;
    @FXML
    private TableColumn<Ingredient, String> ingredientNameColumn;
    @FXML
    private TableColumn<Ingredient, String> ingredientPreparationColumn;
    @FXML
    private TextField ingredientFilterField;

    @FXML
    private TableView<Category> categoryTable;
    @FXML
    private TableColumn<Category, String> categoryNameColumn;
    @FXML
    private TableColumn<Category, String> categoryDescriptionColumn;
    @FXML
    private TextField categoryFilterField;

    private ObservableList<Meal> meals = FXCollections.observableArrayList();
    private ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
    private ObservableList<Category> categories = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Bind columns to data properties
        mealNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        mealCategoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategory().getName()));

        ingredientNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        ingredientPreparationColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPreparationMethod()));

        categoryNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        categoryDescriptionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));

        // Load initial data
        loadData();
        mealTable.setItems(meals);
        ingredientTable.setItems(ingredients);
        categoryTable.setItems(categories);

        // Add filtering
        mealFilterField.textProperty().addListener((observable, oldValue, newValue) -> filterMeals(newValue));
        ingredientFilterField.textProperty().addListener((observable, oldValue, newValue) -> filterIngredients(newValue));
        categoryFilterField.textProperty().addListener((observable, oldValue, newValue) -> filterCategories(newValue));

        // Add selection event for Meal table
        mealTable.setOnMouseClicked(this::onMealSelected);
    }

    private void loadData() {
        // Populate with sample data
        Category fastFood = new Category(1L, "Fast Food", "dsadad");
        Category healthy = new Category(2L, "Healthy", "sdadada");

        Ingredient cheese = new Ingredient(1L, "Cheese", fastFood, BigDecimal.valueOf(200), "Grated");
        Ingredient lettuce = new Ingredient(2L, "Lettuce", healthy, BigDecimal.valueOf(15), "Chopped");
        Ingredient tomato = new Ingredient(3L, "Tomato", healthy, BigDecimal.valueOf(25), "Sliced");

        Set<Ingredient> pizzaIngredients = new HashSet<>();
        pizzaIngredients.add(cheese);
        pizzaIngredients.add(tomato);

        Set<Ingredient> saladIngredients = new HashSet<>();
        saladIngredients.add(lettuce);
        saladIngredients.add(tomato);

        meals.addAll(
                new Meal(1L, "Pizza", fastFood, pizzaIngredients, BigDecimal.valueOf(8.99), 1200),
                new Meal(2L, "Salad", healthy, saladIngredients, BigDecimal.valueOf(5.99), 300)
        );

        ingredients.addAll(pizzaIngredients);
        ingredients.addAll(saladIngredients);

        categories.addAll(fastFood, healthy);
    }

    private void filterMeals(String filter) {
        mealTable.setItems(meals.filtered(meal -> meal.getName().toLowerCase().contains(filter.toLowerCase())));
    }

    private void filterIngredients(String filter) {
        ingredientTable.setItems(ingredients.filtered(ingredient -> ingredient.getName().toLowerCase().contains(filter.toLowerCase())));
    }

    private void filterCategories(String filter) {
        categoryTable.setItems(categories.filtered(category -> category.getDescription().toLowerCase().contains(filter.toLowerCase())));
    }

    private void onMealSelected(MouseEvent event) {
        Meal selectedMeal = mealTable.getSelectionModel().getSelectedItem();
        if (selectedMeal != null) {
            // Update Ingredients table based on selected Meal
            Set<Ingredient> mealIngredients = selectedMeal.getIngredient();
            ingredientTable.setItems(FXCollections.observableArrayList(mealIngredients));
        }
    }
}
