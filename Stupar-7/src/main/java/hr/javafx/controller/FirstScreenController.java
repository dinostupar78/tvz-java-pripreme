package hr.javafx.controller;

import hr.javafx.main.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class FirstScreenController {
    public Scene showItemSearchScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 500, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
       return scene;
    }

}