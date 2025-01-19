package hr.javafx.controller;

import hr.javafx.restaurant.model.*;
import hr.javafx.restaurant.repositoryFile.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static javafx.collections.FXCollections.observableArrayList;

public class OrderAddController {
    @FXML
    private ComboBox<Restaurant> orderComboBoxRestaurant;

    @FXML
    private TextField orderTextFieldMeals;

    @FXML
    private ComboBox<Meal> orderComboBoxMeals;

    @FXML
    private ComboBox<String> orderTimeComboBox;

    @FXML
    private ComboBox<Deliverer> orderComboBoxDeliverers;

    @FXML
    private DatePicker orderDatePickerDeliveryDateAndTime;

    private CategoryFileRepository<Category> categoryRepository = new CategoryFileRepository<>();
    private AddressFileRepository<Address> addressRepository = new AddressFileRepository<>();
    private ContractFileRepository<Contract> contractRepository = new ContractFileRepository<>();
    private MealFileRepository<Meal> mealsRepository = new MealFileRepository<>(categoryRepository);
    private ChefFileRepository<Chef> chefRepository = new ChefFileRepository<>(contractRepository);
    private WaiterFileRepository<Waiter> waiterRepository = new WaiterFileRepository(contractRepository);
    private DelivererFileRepository<Deliverer> delivererRepository = new DelivererFileRepository<>(contractRepository);
    private RestaurantFileRepository<Restaurant> restaurantRepository = new RestaurantFileRepository<>(addressRepository,
            mealsRepository, chefRepository, waiterRepository, delivererRepository);
    private OrderFileRepository<Order> orderRepository = new OrderFileRepository<>(restaurantRepository, mealsRepository, delivererRepository);

    private Set<Meal> selectedMeals = new HashSet<>();

    public void initialize(){
        orderComboBoxMeals.getItems().addAll(mealsRepository.findAll());
        orderComboBoxRestaurant.getItems().addAll(restaurantRepository.findAll());
        orderComboBoxDeliverers.getItems().addAll(delivererRepository.findAll());

        orderTimeComboBox.getItems().addAll(
                "00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00",
                "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00",
                "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"
        );

        Set<Meal> meals = mealsRepository.findAll();

        orderComboBoxMeals.setItems(observableArrayList(meals));

        orderComboBoxMeals.setConverter(new StringConverter<Meal>() {

            @Override
            public String toString(Meal meal) {
                return meal != null ? meal.getName() : "";
            }

            @Override
            public Meal fromString(String s) {
                return null;
            }
        });
    }

    public void addMealToSelection() {
        Meal selectedMeal = orderComboBoxMeals.getValue();

        if (selectedMeal != null) {
            selectedMeals.add(selectedMeal);

            // Update the TextField to show selected meals
            String selectedMealsText = selectedMeals.stream()
                    .map(Meal::getName)
                    .collect(Collectors.joining(", "));
            orderTextFieldMeals.setText(selectedMealsText);

            // Reset the ComboBox selection
            orderComboBoxMeals.setValue(null);
        }
    }

    public void addNewOrder(){
        Restaurant orderRestaurantName = orderComboBoxRestaurant.getValue();
        Set<Restaurant> selectRestaurant = new HashSet<>();
        selectRestaurant.add(orderRestaurantName);

        Meal selectedMeal = orderComboBoxMeals.getValue();
        List<Meal> selectMeals = new ArrayList<>();
        selectMeals.add(selectedMeal);

        Deliverer orderDeliverer = orderComboBoxDeliverers.getValue();
        Set<Deliverer> selectDeliverer = new HashSet<>();
        selectDeliverer.add(orderDeliverer);

        LocalDate orderDeliveryDate = orderDatePickerDeliveryDateAndTime.getValue();
        String selectedTime = orderTimeComboBox.getValue();
        String dateTimeString = orderDeliveryDate.toString() + " " + selectedTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime orderDateTime = LocalDateTime.parse(dateTimeString, formatter);

        Order order = new Order(null, orderRestaurantName, new ArrayList<>(selectedMeals), orderDeliverer, orderDateTime);

        orderRepository.save(order);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uspješno spremanje nove narudžbe");
        alert.setHeaderText("Narudžba je uspješno spremljena");
        StringBuilder sb = new StringBuilder();
        sb.append("Ime restorana: ")
                .append(orderRestaurantName)
                .append("\n");
        sb.append("Naručena jela: ")
                .append(new ArrayList<>(selectedMeals))
                .append("\n");
        sb.append("Dostavljači narudžbe: ")
                .append(orderDeliverer)
                .append("\n");
        sb.append("Konobari restorana: ")
                .append(orderDeliverer)
                .append("\n");
        sb.append("Vrijeme dostave: ")
                .append(orderDateTime)
                .append("\n");
        alert.setContentText(sb.toString());
        alert.showAndWait();

        orderComboBoxRestaurant.setValue(null);
        orderComboBoxMeals.setValue(null);
        orderTextFieldMeals.clear();
        orderComboBoxDeliverers.setValue(null);
        orderDatePickerDeliveryDateAndTime.setValue(null);
        orderTimeComboBox.setValue(null);

    }

}