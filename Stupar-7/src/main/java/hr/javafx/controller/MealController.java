package hr.javafx.controller;

import hr.javafx.restaurant.model.Meal;
import hr.javafx.restaurant.repository.CategoryRepository;
import hr.javafx.restaurant.repository.MealsRepository;
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

    private CategoryRepository categoryRepository = new CategoryRepository<>();
    private MealsRepository mealsRepository = new MealsRepository<>(categoryRepository);

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
    }

    public void filterMeals(){
        Set<Meal> initialMealList = mealsRepository.findAll();

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

        ObservableList<Meal> mealObservableList = javafx.collections.FXCollections.observableArrayList(initialMealList);
        mealTableView.setItems(mealObservableList);

    }





}
