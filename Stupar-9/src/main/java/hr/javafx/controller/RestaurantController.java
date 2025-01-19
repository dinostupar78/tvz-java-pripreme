package hr.javafx.controller;

import hr.javafx.restaurant.model.Restaurant;
import hr.javafx.restaurant.repositoryFile.*;
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

public class RestaurantController {
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
    private TextField restaurantTextFieldID;

    @FXML
    private TextField restaurantTextFieldName;

    @FXML
    private TextField restaurantTextFieldAddress;

    @FXML
    private TextField restaurantTextFieldMeals;

    @FXML
    private TextField restaurantTextFieldChefs;

    @FXML
    private TextField restaurantTextFieldWaiters;

    @FXML
    private TextField restaurantTextFieldDeliverers;

    @FXML
    private TableView<Restaurant> restaurantTableView;

    @FXML
    private TableColumn<Restaurant, String> restaurantColumnID;

    @FXML
    private TableColumn<Restaurant, String> restaurantColumnName;

    @FXML
    private TableColumn<Restaurant, String> restaurantColumnAddress;

    @FXML
    private TableColumn<Restaurant, String> restaurantColumnMeals;

    @FXML
    private TableColumn<Restaurant, String> restaurantColumnChefs;

    @FXML
    private TableColumn<Restaurant, String> restaurantColumnWaiters;

    @FXML
    private TableColumn<Restaurant, String> restaurantColumnDeliverers;

    private CategoryFileRepository categoryRepository = new CategoryFileRepository<>();
    private AddressFileRepository addressRepository = new AddressFileRepository<>(); // POPRAVIT SVUGDJE KAD DODE VRIJEME
    private ContractFileRepository contractRepository = new ContractFileRepository<>();
    private MealFileRepository mealsRepository = new MealFileRepository<>(categoryRepository);
    private ChefFileRepository chefRepository = new ChefFileRepository<>(contractRepository);
    private WaiterFileRepository waiterRepository = new WaiterFileRepository(contractRepository);
    private DelivererFileRepository delivererRepository = new DelivererFileRepository<>(contractRepository);
    private RestaurantFileRepository restaurantRepository = new RestaurantFileRepository<>(addressRepository,
            mealsRepository, chefRepository, waiterRepository, delivererRepository);

    public void initialize(){
        restaurantColumnID.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getId()))
        );

        restaurantColumnName.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getName()))
        );

        restaurantColumnAddress.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getAddress().getCity()))
        );

        restaurantColumnMeals.setCellValueFactory(cellData -> {
            String mealNames = cellData.getValue().getMeals().stream()
                    .map(meal -> meal.getName())
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(mealNames);
        });

        restaurantColumnChefs.setCellValueFactory(cellData -> {
            String chefNames = cellData.getValue().getChefs().stream()
                    .map(chef -> chef.getFirstName())
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(chefNames);
        });

        restaurantColumnWaiters.setCellValueFactory(cellData -> {
            String waiterNames = cellData.getValue().getWaiters().stream()
                    .map(waiter -> waiter.getFirstName())
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(waiterNames);
        });

        restaurantColumnDeliverers.setCellValueFactory(cellData -> {
            String delivererNames = cellData.getValue().getDeliverers().stream()
                    .map(deliverer -> deliverer.getFirstName())
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(delivererNames);
        });

        restaurantTableView.getSortOrder().add(restaurantColumnID);
    }

    public void filterRestaurants() {
        Set<Restaurant> initialRestaurantList = restaurantRepository.findAll();

        String restaurantID = restaurantTextFieldID.getText();
        if (!restaurantID.isEmpty()) {
            initialRestaurantList = initialRestaurantList.stream()
                    .filter(restaurant -> restaurant.getId().toString().contains(restaurantID))
                    .collect(Collectors.toSet());
        }

        String restaurantName = restaurantTextFieldName.getText();
        if (!restaurantName.isEmpty()) {
            initialRestaurantList = initialRestaurantList.stream()
                    .filter(restaurant -> restaurant.getName().toString().contains(restaurantName))
                    .collect(Collectors.toSet());
        }

        String restaurantAddress = restaurantTextFieldAddress.getText();
        if (!restaurantAddress.isEmpty()) {
            initialRestaurantList = initialRestaurantList.stream()
                    .filter(restaurant -> restaurant.getAddress().getCity().toLowerCase().contains(restaurantAddress.toLowerCase()))
                    .collect(Collectors.toSet());
        }

        String orderMeals = restaurantTextFieldMeals.getText();
        if (!orderMeals.isEmpty()) {
            initialRestaurantList = initialRestaurantList.stream()
                    .filter(restaurant -> restaurant.getMeals().stream()
                            .anyMatch(meal -> meal.getName().toLowerCase().contains(orderMeals.toLowerCase())))
                    .collect(Collectors.toSet());
        }

        String restaurantChefs = restaurantTextFieldChefs.getText().trim();
        if (!restaurantChefs.isEmpty()) {
            initialRestaurantList = initialRestaurantList.stream()
                    .filter(restaurant -> restaurant.getChefs().stream()
                            .anyMatch(chef -> chef.getFirstName().toLowerCase().contains(restaurantChefs.toLowerCase()) ||
                                    chef.getLastName().toLowerCase().contains(restaurantChefs.toLowerCase())))
                    .collect(Collectors.toSet());
        }

        String restaurantWaiters = restaurantTextFieldWaiters.getText().trim();
        if (!restaurantWaiters.isEmpty()) {
            initialRestaurantList = initialRestaurantList.stream()
                    .filter(restaurant -> restaurant.getWaiters().stream()
                            .anyMatch(waiter -> waiter.getFirstName().toLowerCase().contains(restaurantWaiters.toLowerCase()) ||
                                    waiter.getLastName().toLowerCase().contains(restaurantWaiters.toLowerCase())))
                    .collect(Collectors.toSet());
        }

        String restaurantDeliverers = restaurantTextFieldDeliverers.getText().trim();
        if (!restaurantDeliverers.isEmpty()) {
            initialRestaurantList = initialRestaurantList.stream()
                    .filter(restaurant -> restaurant.getDeliverers().stream()
                            .anyMatch(deliverer -> deliverer.getFirstName().toLowerCase().contains(restaurantDeliverers.toLowerCase()) ||
                                    deliverer.getLastName().toLowerCase().contains(restaurantDeliverers.toLowerCase())))
                    .collect(Collectors.toSet());
        }

        ObservableList<Restaurant> restaurantObservableList = observableArrayList(initialRestaurantList);

        SortedList<Restaurant> sortedList = new SortedList<>(restaurantObservableList);

        sortedList.comparatorProperty().bind(restaurantTableView.comparatorProperty());

        restaurantTableView.setItems(sortedList);
    }

}
