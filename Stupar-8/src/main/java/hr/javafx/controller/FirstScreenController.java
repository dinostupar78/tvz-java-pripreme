package hr.javafx.controller;

import hr.javafx.main.MainApplication;
import hr.javafx.utils.HandleAddClickUtils;
import hr.javafx.utils.HandleSearchClickUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class FirstScreenController {
    public Scene showItemSearchScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/hr/javafx/startingScreen.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 800, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scene;
    }

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

    public void onAddCategoryClick(ActionEvent event) {
        HandleAddClickUtils addClickUtils = new HandleAddClickUtils();
        addClickUtils.handleAddClickCategories(event);
    }

    public void onAddIngredientClick(ActionEvent event) {
        HandleAddClickUtils addClickUtils = new HandleAddClickUtils();
        addClickUtils.handleAddClickIngredients(event);
    }

    public void onAddMealClick(ActionEvent event) {
        HandleAddClickUtils addClickUtils = new HandleAddClickUtils();
        addClickUtils.handleAddClickMeals(event);
    }

    public void onAddContractClick(ActionEvent event) {
        HandleAddClickUtils addClickUtils = new HandleAddClickUtils();
        addClickUtils.handleAddClickContracts(event);
    }



}