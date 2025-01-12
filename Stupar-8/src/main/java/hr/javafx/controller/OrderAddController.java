package hr.javafx.controller;

import hr.javafx.restaurant.model.Deliverer;
import hr.javafx.restaurant.model.Meal;
import hr.javafx.restaurant.model.Restaurant;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class OrderAddController {
    @FXML
    private ComboBox<Restaurant> orderComboBoxRestaurant;

    @FXML
    private ComboBox<Meal> orderComboBoxMeals;

    @FXML
    private ComboBox<Deliverer> orderComboBoxDeliverers;

    @FXML
    private DatePicker orderDatePickerDeliveryDateAndTime;

    public void addNewOrder(){

    }


}
