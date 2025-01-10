package hr.javafx.controller;

import hr.javafx.restaurant.model.Ingredient;
import hr.javafx.restaurant.repository.CategoryRepository;
import hr.javafx.restaurant.repository.IngredientRepository;
import hr.javafx.utils.HandleSearchClickUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Set;
import java.util.stream.Collectors;

public class IngredientController {
    public void onSearchCategoryClick(ActionEvent event) {
        HandleSearchClickUtils searchClickUtils = new HandleSearchClickUtils();
        searchClickUtils.handleSearchClickCategories(event);
    }

    public void onSearchIngredientClick(ActionEvent event) {
        HandleSearchClickUtils searchClickUtils = new HandleSearchClickUtils();
        searchClickUtils.handleSearchClickIngredients(event);
    }

    public void onSearchMealClick(ActionEvent event) {
        HandleSearchClickUtils searchClickUtils = new HandleSearchClickUtils();
        searchClickUtils.handleSearchClickMeals(event);
    }

    public void onSearchContractClick(ActionEvent event) {
        HandleSearchClickUtils searchClickUtils = new HandleSearchClickUtils();
        searchClickUtils.handleSearchClickContracts(event);
    }

    public void onSearchChefClick(ActionEvent event) {
        HandleSearchClickUtils searchClickUtils = new HandleSearchClickUtils();
        searchClickUtils.handleSearchClickChefs(event);
    }

    public void onSearchWaiterClick(ActionEvent event) {
        HandleSearchClickUtils searchClickUtils = new HandleSearchClickUtils();
        searchClickUtils.handleSearchClickWaiters(event);
    }

    public void onSearchDelivererClick(ActionEvent event) {
        HandleSearchClickUtils searchClickUtils = new HandleSearchClickUtils();
        searchClickUtils.handleSearchClickDeliverers(event);
    }

    public void onSearchRestaurantClick(ActionEvent event) {
        HandleSearchClickUtils searchClickUtils = new HandleSearchClickUtils();
        searchClickUtils.handleSearchClickRestaurants(event);
    }

    public void onSearchOrderClick(ActionEvent event) {
        HandleSearchClickUtils searchClickUtils = new HandleSearchClickUtils();
        searchClickUtils.handleSearchClickOrders(event);
    }

    @FXML
    private TextField ingredientTextFieldID;
    @FXML
    private TextField ingredientTextFieldName;
    @FXML
    private TextField ingredientTextFieldCategory;
    @FXML
    private TextField ingredientTextFieldKcal;
    @FXML
    private TextField ingredientTextFieldPreparationMethod;
    @FXML
    private TableView<Ingredient> ingredientTableView;
    @FXML
    private TableColumn<Ingredient, String> ingredientColumnID;
    @FXML
    private TableColumn<Ingredient, String> ingredientColumnName;
    @FXML
    private TableColumn<Ingredient, String> ingredientColumnCategory;
    @FXML
    private TableColumn<Ingredient, String> ingredientColumnKcal;
    @FXML
    private TableColumn<Ingredient, String> ingredientColumnPreparationMethod;



    private CategoryRepository categoryRepository = new CategoryRepository<>();
    private IngredientRepository ingredientRepository = new IngredientRepository<>(categoryRepository);

    public void initialize() {
        ingredientColumnID.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getId()))
        );

        ingredientColumnName.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getName()))
        );

        ingredientColumnCategory.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getCategory().getName()))
        );

        ingredientColumnKcal.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getKcal()))
        );

        ingredientColumnPreparationMethod.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getPreparationMethod()))
        );

    }

    public void filterIngredients() {
        Set<Ingredient> initialIngredientList = ingredientRepository.findAll();

        String ingredientID = ingredientTextFieldID.getText();
        if (!ingredientID.isEmpty()) {
            initialIngredientList = initialIngredientList.stream()
                    .filter(ingredient -> ingredient.getId().toString().contains(ingredientID))
                    .collect(Collectors.toSet());
        }

        String ingredientName = ingredientTextFieldName.getText();
        if (!ingredientName.isEmpty()) {
            initialIngredientList = initialIngredientList.stream()
                    .filter(ingredient -> ingredient.getName().toLowerCase().contains(ingredientName.toLowerCase()))
                    .collect(Collectors.toSet());
        }

        String ingredientCategory = ingredientTextFieldCategory.getText();
        if (!ingredientCategory.isEmpty()) {
            initialIngredientList = initialIngredientList.stream()
                    .filter(ingredient -> ingredient.getCategory().getName().toLowerCase().contains(ingredientCategory.toLowerCase()))
                    .collect(Collectors.toSet());
        }

        String ingredientKcal = ingredientTextFieldKcal.getText();
        if (!ingredientKcal.isEmpty()) {
            initialIngredientList = initialIngredientList.stream()
                    .filter(ingredient -> String.valueOf(ingredient.getKcal()).contains(ingredientKcal))
                    .collect(Collectors.toSet());
        }

        String ingredientPreparationMethod = ingredientTextFieldPreparationMethod.getText();
        if (!ingredientPreparationMethod.isEmpty()) {
            initialIngredientList = initialIngredientList.stream()
                    .filter(ingredient -> ingredient.getPreparationMethod().toLowerCase().contains(ingredientPreparationMethod.toLowerCase()))
                    .collect(Collectors.toSet());
        }


        ObservableList<Ingredient> ingredientObservableList = javafx.collections.FXCollections.observableArrayList(initialIngredientList);
        ingredientTableView.setItems(ingredientObservableList);


    }




}
