package hr.javafx.controller;

import hr.javafx.restaurant.model.Category;
import hr.javafx.restaurant.model.Ingredient;
import hr.javafx.restaurant.model.Meal;
import hr.javafx.restaurant.repositoryDatabase.CategoryDatabaseRepository;
import hr.javafx.restaurant.repositoryDatabase.IngredientDatabaseRepository;
import hr.javafx.restaurant.repositoryDatabase.MealDatabaseRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Set;
import java.util.stream.Collectors;

import static javafx.collections.FXCollections.observableArrayList;

public class ElseController {
    @FXML
    private TextField mealTextField;
    @FXML private TableView<Meal> mealTableView;
    @FXML private TableColumn<Meal, String> mealColumnID;
    @FXML private TableColumn<Meal, String> mealColumnName;
    @FXML private TableColumn<Meal, String> mealColumnCategory;
    @FXML private TableColumn<Meal, String> mealColumnPrice;
    @FXML private TableColumn<Meal, String> mealColumnCalories;

    @FXML private TextField ingredientTextField;
    @FXML private TableView<Ingredient> ingredientTableView;
    @FXML private TableColumn<Ingredient, String> ingredientColumnID;
    @FXML private TableColumn<Ingredient, String> ingredientColumnName;
    @FXML private TableColumn<Ingredient, String> ingredientColumnCategory;
    @FXML private TableColumn<Ingredient, String> ingredientColumnKcal;
    @FXML private TableColumn<Ingredient, String> ingredientColumnPreparationMethod;

    @FXML private TextField categoryTextField;
    @FXML private TableView<Category> categoryTableView;
    @FXML private TableColumn<Category, String> categoryColumnID;
    @FXML private TableColumn<Category, String> categoryColumnName;
    @FXML private TableColumn<Category, String> categoryColumnDescription;

    private MealDatabaseRepository mealRepository = new MealDatabaseRepository();
    private IngredientDatabaseRepository ingredientRepository = new IngredientDatabaseRepository();
    private CategoryDatabaseRepository categoryRepository = new CategoryDatabaseRepository();

    public void initialize() {
        mealColumnID.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        mealColumnName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        mealColumnCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().getName()));
        mealColumnPrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));
        mealColumnCalories.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCalories())));

        ingredientColumnID.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        ingredientColumnName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        ingredientColumnCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().getName()));
        ingredientColumnKcal.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getKcal())));
        ingredientColumnPreparationMethod.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPreparationMethod()));

        categoryColumnID.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));
        categoryColumnName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        categoryColumnDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
    }

    public void filterMeals() {
        Set<Meal> filteredMeals = mealRepository.findAll();
        String mealFilter = mealTextField.getText().toLowerCase();
        if (!mealFilter.isEmpty()) {
            filteredMeals = filteredMeals.stream()
                    .filter(meal -> meal.getName().toLowerCase().contains(mealFilter))
                    .collect(Collectors.toSet());
        }
        ObservableList<Meal> mealObservableList = observableArrayList(filteredMeals);
        SortedList<Meal> sortedList = new SortedList<>(mealObservableList);
        sortedList.comparatorProperty().bind(mealTableView.comparatorProperty());
        mealTableView.setItems(sortedList);
    }

    public void filterIngredients() {
        Set<Ingredient> filteredIngredients = ingredientRepository.findAll();
        String ingredientFilter = ingredientTextField.getText().toLowerCase();
        if (!ingredientFilter.isEmpty()) {
            filteredIngredients = filteredIngredients.stream()
                    .filter(ingredient -> ingredient.getName().toLowerCase().contains(ingredientFilter))
                    .collect(Collectors.toSet());
        }
        ObservableList<Ingredient> ingredientObservableList = observableArrayList(filteredIngredients);
        SortedList<Ingredient> sortedList = new SortedList<>(ingredientObservableList);
        sortedList.comparatorProperty().bind(ingredientTableView.comparatorProperty());
        ingredientTableView.setItems(sortedList);
    }

    public void filterCategories() {
        Set<Category> filteredCategories = categoryRepository.findAll();
        String categoryFilter = categoryTextField.getText().toLowerCase();
        if (!categoryFilter.isEmpty()) {
            filteredCategories = filteredCategories.stream()
                    .filter(category -> category.getName().toLowerCase().contains(categoryFilter))
                    .collect(Collectors.toSet());
        }
        ObservableList<Category> categoryObservableList = observableArrayList(filteredCategories);
        SortedList<Category> sortedList = new SortedList<>(categoryObservableList);
        sortedList.comparatorProperty().bind(categoryTableView.comparatorProperty());
        categoryTableView.setItems(sortedList);
    }
}
