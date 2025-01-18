package hr.javafx.controller;

import hr.javafx.restaurant.model.Order;
import hr.javafx.restaurant.repository.*;
import hr.javafx.utils.HandleSearchClickUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

import static javafx.collections.FXCollections.observableArrayList;

public class OrderController {
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
    private TextField orderTextFieldID;

    @FXML
    private TextField orderTextFieldRestaurant;

    @FXML
    private TextField orderTextFieldMeals;

    @FXML
    private TextField orderTextFieldDeliverer;

    @FXML
    private TextField orderTextFieldDateAndTime;

    @FXML
    private TableView<Order> orderTableView;

    @FXML
    private TableColumn<Order, String> orderColumnID;

    @FXML
    private TableColumn<Order, String> orderColumnName;

    @FXML
    private TableColumn<Order, String> orderColumnMeals;

    @FXML
    private TableColumn<Order, String> orderColumnDeliverer;

    @FXML
    private TableColumn<Order, String> orderColumnDateAndTime;

    private CategoryRepository categoryRepository = new CategoryRepository<>();
    private AddressRepository addressRepository = new AddressRepository<>();
    private ContractRepository contractRepository = new ContractRepository<>();
    private MealsRepository mealsRepository = new MealsRepository<>(categoryRepository);
    private ChefRepository chefRepository = new ChefRepository<>(contractRepository);
    private WaiterRepository waiterRepository = new WaiterRepository(contractRepository);
    private DelivererRepository delivererRepository = new DelivererRepository<>(contractRepository);
    private RestaurantRepository restaurantRepository = new RestaurantRepository<>(addressRepository, mealsRepository, chefRepository, waiterRepository, delivererRepository);
    private OrderRepository orderRepository = new OrderRepository<>(restaurantRepository, mealsRepository, delivererRepository);

    public void initialize(){
        orderColumnID.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getId()))
        );

        orderColumnName.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getRestaurant().getName()))
                );

        orderColumnMeals.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getMeals().stream()
                                .map(meal -> meal.getName())
                                .collect(Collectors.joining(", "))
                )
        );

        orderColumnDeliverer.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getDeliverer().getFirstName() + " " +
                                cellData.getValue().getDeliverer().getLastName()
                )
        );

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        orderColumnDateAndTime.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getDeliveryDateAndTime().format(formatter) // Format the date and time
                )
        );

        orderTableView.getSortOrder().add(orderColumnID);

    }

    public void filterOrders(){
        Set<Order> initialOrderList = orderRepository.findAll();

        String orderID = orderTextFieldID.getText();
        if(!orderID.isEmpty()){
            initialOrderList = initialOrderList.stream()
                    .filter(order -> order.getId().toString().contains(orderID))
                    .collect(Collectors.toSet());
        }

        String orderRestaurant = orderTextFieldRestaurant.getText();
        if(!orderRestaurant.isEmpty()){
            initialOrderList = initialOrderList.stream()
                    .filter(order -> order.getRestaurant().getName().toString().contains(orderRestaurant))
                    .collect(Collectors.toSet());
        }

        String orderMeals = orderTextFieldMeals.getText();
        if (!orderMeals.isEmpty()) {
            initialOrderList = initialOrderList.stream()
                    .filter(order -> order.getMeals().stream()
                            .anyMatch(meal -> meal.getName().contains(orderMeals)))
                    .collect(Collectors.toSet());
        }

        String orderDeliverer = orderTextFieldDeliverer.getText();
        if(!orderDeliverer.isEmpty()){
            initialOrderList = initialOrderList.stream()
                    .filter(order -> order.getDeliverer().getFirstName().toString().contains(orderDeliverer))
                    .collect(Collectors.toSet());
        }

        String orderDateAndTime = orderTextFieldDateAndTime.getText();
        if(!orderDateAndTime.isEmpty()){
            initialOrderList = initialOrderList.stream()
                    .filter(order -> order.getDeliveryDateAndTime().toString().contains(orderDateAndTime))
                    .collect(Collectors.toSet());
        }

        ObservableList<Order> orderObservableList = observableArrayList(initialOrderList);

        SortedList<Order> sortedList = new SortedList<>(orderObservableList);

        sortedList.comparatorProperty().bind(orderTableView.comparatorProperty());

        orderTableView.setItems(sortedList);
    }

}
