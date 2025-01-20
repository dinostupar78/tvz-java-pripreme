package hr.javafx.controller;

import hr.javafx.restaurant.model.*;
import hr.javafx.restaurant.repositoryDatabase.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.util.HashSet;
import java.util.Set;

import static javafx.collections.FXCollections.observableArrayList;

public class RestaurantAddController {
    @FXML
    private TextField restaurantTextFieldName;

    @FXML
    private ComboBox<Address> restaurantComboBoxAddress;

    @FXML
    private ComboBox<Meal> restaurantComboBoxMeals;

    @FXML
    private ComboBox<Chef> restaurantComboBoxChefs;

    @FXML
    private ComboBox<Waiter> restaurantComboBoxWaiters;

    @FXML
    private ComboBox<Deliverer> restaurantComboBoxDeliverers;

    //private ContractFileRepository<Contract> contractRepository = new ContractFileRepository<>();
    //private CategoryFileRepository<Category> categoryRepository = new CategoryFileRepository<>();
    //private AddressFileRepository<Address> addressRepository;
    //private MealFileRepository<Meal> mealsRepository;
    //private ChefFileRepository<Chef> chefRepository;
    //private WaiterFileRepository<Waiter> waiterRepository;
    //private DelivererFileRepository<Deliverer> delivererRepository;
    //private RestaurantFileRepository<Restaurant> restaurantRepository;

    private ContractDatabaseRepository<Contract> contractRepository = new ContractDatabaseRepository<>();
    private CategoryDatabaseRepository<Category> categoryRepository = new CategoryDatabaseRepository<>();
    private AddressDatabaseRepository<Address> addressRepository;
    private MealDatabaseRepository<Meal> mealsRepository;
    private ChefDatabaseRepository<Chef> chefRepository;
    private WaiterDatabaseRepository<Waiter> waiterRepository;
    private DelivererDatabaseRepository<Deliverer> delivererRepository;
    private RestaurantDatabaseRepository<Restaurant> restaurantRepository;

    public RestaurantAddController() {
        this.addressRepository = new AddressDatabaseRepository<>();
        this.mealsRepository = new MealDatabaseRepository<>();
        this.chefRepository = new ChefDatabaseRepository<>();
        this.waiterRepository = new WaiterDatabaseRepository<>();
        this.delivererRepository = new DelivererDatabaseRepository<>();
        this.restaurantRepository = new RestaurantDatabaseRepository<>();
    }

    public void initialize(){
        restaurantComboBoxAddress.getItems().addAll(addressRepository.findAll());
        restaurantComboBoxMeals.getItems().addAll(mealsRepository.findAll());
        restaurantComboBoxChefs.getItems().addAll(chefRepository.findAll());
        restaurantComboBoxWaiters.getItems().addAll(waiterRepository.findAll());
        restaurantComboBoxDeliverers.getItems().addAll(delivererRepository.findAll());

        Set<Meal> meals = mealsRepository.findAll();

        restaurantComboBoxMeals.setItems(observableArrayList(meals));

        restaurantComboBoxMeals.setConverter(new StringConverter<Meal>() {

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

    public void addNewRestaurant() {
        StringBuilder errorMessages = new StringBuilder();

        String restaurantName = restaurantTextFieldName.getText();
        if (restaurantName.isEmpty()) {
            errorMessages.append("Unos imena restorana je obavezan!\n");
        }

        Address selectedAddress = restaurantComboBoxAddress.getValue();
        if (selectedAddress == null) {
            errorMessages.append("Odabir adrese restorana je obavezan!\n");
        }

        Meal selectedMeal = restaurantComboBoxMeals.getValue();
        if (selectedMeal == null) {
            errorMessages.append("Odabir jela restorana je obavezan!\n");
        }

        Chef selectedChef = restaurantComboBoxChefs.getValue();
        if (selectedChef == null) {
            errorMessages.append("Odabir kuhara restorana je obavezan!\n");
        }

        Waiter selectedWaiter = restaurantComboBoxWaiters.getValue();
        if (selectedWaiter == null) {
            errorMessages.append("Odabir konobara restorana je obavezan!\n");
        }

        Deliverer selectedDeliverer = restaurantComboBoxDeliverers.getValue();
        if (selectedDeliverer == null) {
            errorMessages.append("Odabir dostavljača restorana je obavezan!\n");
        }

        if (errorMessages.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pogreške kod unosa novog restorana");
            alert.setHeaderText("Restoran nije spremljen zbog pogreški kod unosa");
            alert.setContentText(errorMessages.toString());
            alert.showAndWait();
        } else {
            Set<Meal> selectedMeals = new HashSet<>();
            selectedMeals.add(selectedMeal);

            Set<Chef> selectedChefs = new HashSet<>();
            selectedChefs.add(selectedChef);

            Set<Waiter> selectedWaiters = new HashSet<>();
            selectedWaiters.add(selectedWaiter);

            Set<Deliverer> selectedDeliverers = new HashSet<>();
            selectedDeliverers.add(selectedDeliverer);

            Restaurant newRestaurant = new Restaurant(
                    null,
                    restaurantName,
                    selectedAddress,
                    selectedMeals,
                    selectedChefs,
                    selectedWaiters,
                    selectedDeliverers
            );

            restaurantRepository.save(newRestaurant);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uspješno spremanje novog restorana");
            alert.setHeaderText("Restoran je uspješno spremljen");
            StringBuilder sb = new StringBuilder();
            sb.append("Ime restorana: ")
                    .append(restaurantName)
                    .append("\n");
            sb.append("Adresa restorana: ")
                    .append(selectedAddress)
                    .append("\n");
            sb.append("Kuhari restorana: ")
                    .append(selectedChefs)
                    .append("\n");
            sb.append("Konobari restorana: ")
                    .append(selectedWaiters)
                    .append("\n");
            sb.append("Dostavljači restorana: ")
                    .append(selectedDeliverers)
                    .append("\n");
            alert.setContentText(sb.toString());
            alert.showAndWait();

            restaurantTextFieldName.clear();
            restaurantComboBoxAddress.setValue(null);
            restaurantComboBoxMeals.setValue(null);
            restaurantComboBoxChefs.setValue(null);
            restaurantComboBoxWaiters.setValue(null);
            restaurantComboBoxDeliverers.setValue(null);
        }
    }

}
