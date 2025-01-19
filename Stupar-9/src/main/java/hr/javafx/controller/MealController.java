package hr.javafx.controller;

import hr.javafx.restaurant.model.Meal;
import hr.javafx.restaurant.repositoryDatabase.CategoryDatabaseRepository;
import hr.javafx.restaurant.repositoryDatabase.MealDatabaseRepository;
import hr.javafx.utils.HandleSearchClickUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Set;
import java.util.stream.Collectors;

import static javafx.collections.FXCollections.observableArrayList;

public class MealController {
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
    private TextField mealTextFieldID;

    @FXML
    private TextField mealTextFieldName;

    @FXML
    private TextField mealTextFieldCategory;

    @FXML
    private TextField mealTextFieldPrice;

    @FXML
    private TextField mealTextFieldCalories;

    @FXML
    private TableView<Meal> mealTableView;

    @FXML
    private TableColumn<Meal, String> mealColumnID;

    @FXML
    private TableColumn<Meal, String> mealColumnName;

    @FXML
    private TableColumn<Meal, String> mealColumnCategory;

    @FXML
    private TableColumn<Meal, String> mealColumnPrice;

    @FXML
    private TableColumn<Meal, String> mealColumnCalories;

    //private CategoryFileRepository categoryRepository = new CategoryFileRepository<>();
    //private MealFileRepository mealRepository = new MealsFileRepository<>(categoryRepository);

    private CategoryDatabaseRepository categoryRepository = new CategoryDatabaseRepository();
    private MealDatabaseRepository mealRepository = new MealDatabaseRepository();

    public void initialize(){
        mealColumnID.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getId()))
        );

        mealColumnName.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getName()))
        );

        mealColumnCategory.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getCategory().getName()))
        );

        mealColumnPrice.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice()))
        );

        mealColumnCalories.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getCalories()))
        );

        mealTableView.getSortOrder().add(mealColumnID);

    }

    public void filterMeals(){
        Set<Meal> initialMealList = mealRepository.findAll();

        String mealID = mealTextFieldID.getText();
        if(!mealID.isEmpty()){
            initialMealList = initialMealList.stream()
                    .filter(meal -> meal.getId().toString().contains(mealID))
                    .collect(Collectors.toSet());
        }

        String mealName = mealTextFieldName.getText();
        if(!mealName.isEmpty()){
            initialMealList = initialMealList.stream()
                    .filter(meal -> meal.getName().toString().contains(mealName))
                    .collect(Collectors.toSet());
        }

        String mealCategory = mealTextFieldCategory.getText();
        if(!mealCategory.isEmpty()){
            initialMealList = initialMealList.stream()
                    .filter(meal -> meal.getCategory().toString().contains(mealCategory))
                    .collect(Collectors.toSet());
        }

        String mealPrice = mealTextFieldPrice.getText();
        if(!mealPrice.isEmpty()){
            initialMealList = initialMealList.stream()
                    .filter(meal -> meal.getPrice().toString().contains(mealPrice))
                    .collect(Collectors.toSet());
        }

        String mealCalories = mealTextFieldCalories.getText();
        if(!mealCalories.isEmpty()){
            initialMealList = initialMealList.stream()
                    .filter(meal -> meal.getCalories().toString().contains(mealCalories))
                    .collect(Collectors.toSet());
        }

        ObservableList<Meal> mealObservableList = observableArrayList(initialMealList);

        SortedList<Meal> sortedList = new SortedList<>(mealObservableList);

        sortedList.comparatorProperty().bind(mealTableView.comparatorProperty());

        mealTableView.setItems(sortedList);

    }
}
