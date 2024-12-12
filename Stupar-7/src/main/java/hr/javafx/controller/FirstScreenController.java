package hr.javafx.controller;

import hr.javafx.main.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

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

    public void handleSearchClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hr/javafx/categoriesSearch.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            MenuItem menuItem = (MenuItem) event.getSource();
            Stage stage = (Stage) ((MenuItem) menuItem).getParentPopup().getOwnerWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Log and debug any issues with loading the FXML
        }
    }




}