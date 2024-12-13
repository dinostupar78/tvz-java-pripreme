package hr.javafx.main;

import hr.javafx.controller.FirstScreenController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(new FirstScreenController().showItemSearchScreen());
        stage.show();
        stage.setTitle("Restaurant Management Application");
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}