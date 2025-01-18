package hr.javafx.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class HandleSearchClickUtils {
    public void handleSearchClickCategories(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hr/javafx/categoriesSearch.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            MenuItem menuItem = (MenuItem) event.getSource();
            Stage stage = (Stage) ((MenuItem) menuItem).getParentPopup().getOwnerWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSearchClickIngredients(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hr/javafx/ingredientsSearch.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            MenuItem menuItem = (MenuItem) event.getSource();
            Stage stage = (Stage) ((MenuItem) menuItem).getParentPopup().getOwnerWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSearchClickMeals(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hr/javafx/mealsSearch.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            MenuItem menuItem = (MenuItem) event.getSource();
            Stage stage = (Stage) ((MenuItem) menuItem).getParentPopup().getOwnerWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSearchClickContracts(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hr/javafx/contractsSearch.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            MenuItem menuItem = (MenuItem) event.getSource();
            Stage stage = (Stage) ((MenuItem) menuItem).getParentPopup().getOwnerWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSearchClickChefs(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hr/javafx/chefsSearch.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            MenuItem menuItem = (MenuItem) event.getSource();
            Stage stage = (Stage) ((MenuItem) menuItem).getParentPopup().getOwnerWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSearchClickWaiters(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hr/javafx/waitersSearch.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            MenuItem menuItem = (MenuItem) event.getSource();
            Stage stage = (Stage) ((MenuItem) menuItem).getParentPopup().getOwnerWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSearchClickDeliverers(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hr/javafx/deliverersSearch.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            MenuItem menuItem = (MenuItem) event.getSource();
            Stage stage = (Stage) ((MenuItem) menuItem).getParentPopup().getOwnerWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSearchClickRestaurants(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hr/javafx/restaurantsSearch.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            MenuItem menuItem = (MenuItem) event.getSource();
            Stage stage = (Stage) ((MenuItem) menuItem).getParentPopup().getOwnerWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSearchClickOrders(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hr/javafx/ordersSearch.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            MenuItem menuItem = (MenuItem) event.getSource();
            Stage stage = (Stage) ((MenuItem) menuItem).getParentPopup().getOwnerWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
