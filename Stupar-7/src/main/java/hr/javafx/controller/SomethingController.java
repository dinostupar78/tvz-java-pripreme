package hr.javafx.controller;

import hr.javafx.restaurant.model.Category;
import hr.javafx.restaurant.model.Ingredient;
import hr.javafx.restaurant.model.Meal;
import hr.javafx.restaurant.repository.MealsRepository;
import hr.javafx.restaurant.repository.IngredientRepository;
import hr.javafx.restaurant.repository.CategoryRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Set;

public class SomethingController {

    private MealsRepository<Meal> mealsRepository;
    private IngredientRepository<Ingredient> ingredientRepository;
    private CategoryRepository<Category> categoryRepository;

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
        categoryRepository = new CategoryRepository<>();
        ingredientRepository = new IngredientRepository<>(categoryRepository);
        mealsRepository = new MealsRepository<>(categoryRepository);

        mealNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        mealCategoryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategory().getName()));

        ingredientNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        ingredientPreparationColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPreparationMethod()));

        categoryNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        categoryDescriptionColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));

        loadCategories();
        loadMeals();
        loadIngredients();

        mealTable.setItems(meals);
        ingredientTable.setItems(ingredients);
        categoryTable.setItems(categories);

        mealFilterField.textProperty().addListener((observable, oldValue, newValue) -> filterMeals(newValue));
        ingredientFilterField.textProperty().addListener((observable, oldValue, newValue) -> filterIngredients(newValue));
        categoryFilterField.textProperty().addListener((observable, oldValue, newValue) -> filterCategories(newValue));
    }

    private void loadCategories() {
        Set<Category> categorySet = categoryRepository.findAll();

        categories.setAll(categorySet);
    }

    private void loadMeals() {
        Set<Meal> mealSet = mealsRepository.findAll();

        meals.setAll(mealSet);
    }

    private void loadIngredients() {
        Set<Ingredient> ingredientSet = ingredientRepository.findAll();

        ingredients.setAll(ingredientSet);
    }

    private void filterMeals(String filter) {
        mealTable.setItems(meals.filtered(meal -> meal.getName().toLowerCase().contains(filter.toLowerCase())));
    }

    private void filterIngredients(String filter) {
        ingredientTable.setItems(ingredients.filtered(ingredient -> ingredient.getName().toLowerCase().contains(filter.toLowerCase())));
    }

    private void filterCategories(String filter) {
        categoryTable.setItems(categories.filtered(category ->
                category.getName().toLowerCase().contains(filter.toLowerCase()) ||
                        category.getDescription().toLowerCase().contains(filter.toLowerCase())
        ));
    }
}
