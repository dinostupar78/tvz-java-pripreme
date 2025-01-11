package hr.javafx.controller;

import hr.javafx.restaurant.model.Category;
import hr.javafx.restaurant.repository.CategoryRepository;
import hr.javafx.utils.HandleSearchClickUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Set;
import java.util.stream.Collectors;

public class CategoryController {
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
    private TextField categoryTextFieldID;

    @FXML
    private TextField categoryTextFieldName;

    @FXML
    private TextField categoryTextFieldDescription;

    @FXML
    private TableView<Category> categoryTableView;

    @FXML
    private TableColumn<Category, String> categoryColumnID;

    @FXML
    private TableColumn<Category, String> categoryColumnName;

    @FXML
    private TableColumn<Category, String> categoryColumnDescription;

    private CategoryRepository categoryRepository = new CategoryRepository();

    public void initialize() {
        /*categoryColumnName.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Category, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Category, String> categoryStringCellDataFeatures) {
                        return new SimpleStringProperty(categoryStringCellDataFeatures.getValue().getName());
                    }
                }
        );*/

        categoryColumnID.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getId()))
                );

        categoryColumnName.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getName()))
                );

        categoryColumnDescription.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getDescription()))
                );

        categoryTableView.getSortOrder().add(categoryColumnID);

    }

    public void filterCategories() {
        // Get all categories from the repository
        Set<Category> initialCategoryList = categoryRepository.findAll();


        String categoryID = categoryTextFieldID.getText();
        if (!categoryID.isEmpty()) {
            initialCategoryList = initialCategoryList.stream()
                    .filter(category -> category.getId().toString().contains(categoryID))
                    .collect(Collectors.toSet());
        }

        String categoryName = categoryTextFieldName.getText();
        if (!categoryName.isEmpty()) {
            initialCategoryList = initialCategoryList.stream()
                    .filter(category -> category.getName().toLowerCase().contains(categoryName.toLowerCase()))
                    .collect(Collectors.toSet());
        }

        String categoryDescription = categoryTextFieldDescription.getText();
        if (!categoryDescription.isEmpty()) {
            initialCategoryList = initialCategoryList.stream()
                    .filter(category -> category.getDescription().toLowerCase().contains(categoryDescription.toLowerCase()))
                    .collect(Collectors.toSet());
        }

        ObservableList<Category> categoryObservableList = FXCollections.observableArrayList(initialCategoryList);

        SortedList<Category> sortedList = new SortedList<>(categoryObservableList);

        sortedList.comparatorProperty().bind(categoryTableView.comparatorProperty());

        categoryTableView.setItems(sortedList);
    }



}
