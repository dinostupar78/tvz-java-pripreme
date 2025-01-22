package hr.javafx.controller;

import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Deliverer;
import hr.javafx.restaurant.repositoryDatabase.ContractDatabaseRepository;
import hr.javafx.restaurant.repositoryDatabase.DelivererDatabaseRepository;
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

public class DelivererController {
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
    private TextField delivererTextFieldID;

    @FXML
    private TextField delivererTextFieldFirstName;

    @FXML
    private TextField delivererTextFieldLastName;

    @FXML
    private TextField delivererTextFieldContract;

    @FXML
    private TextField delivererTextFieldBonus;

    @FXML
    private TableView<Deliverer> delivererTableView;

    @FXML
    private TableColumn<Deliverer, String> delivererColumnID;

    @FXML
    private TableColumn<Deliverer, String> delivererColumnFirstName;

    @FXML
    private TableColumn<Deliverer, String> delivererColumnLastName;

    @FXML
    private TableColumn<Deliverer, String> delivererColumnContract;

    @FXML
    private TableColumn<Deliverer, String> delivererColumnBonus;

    //private ContractFileRepository contractRepository = new ContractFileRepository();
    //private DelivererFileRepository delivererRepository = new DelivererFileRepository<>(contractRepository);

    private ContractDatabaseRepository contractRepository = new ContractDatabaseRepository();
    private DelivererDatabaseRepository delivererRepository = new DelivererDatabaseRepository();

    public void initialize(){
        delivererColumnID.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getId()))
        );

        delivererColumnFirstName.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getFirstName()))
        );

        delivererColumnLastName.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getLastName()))
        );

        delivererColumnContract.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getContract().getContractType()))
        );

        delivererColumnBonus.setCellValueFactory(cellData -> {
            Deliverer deliverer = cellData.getValue();
            Bonus bonus = deliverer.getBonusDostavljaca();
            return new SimpleStringProperty(bonus.iznosBonusaNaOsnovnuPlacu().toString());
        });

        delivererTableView.getSortOrder().add(delivererColumnID);

    }

    public void filterDeliverers(){
        Set<Deliverer> initialDelivererList = delivererRepository.findAll();

        String delivererID = delivererTextFieldID.getText();
        if(!delivererID.isEmpty()){
            initialDelivererList = initialDelivererList.stream()
                    .filter(deliverer -> deliverer.getId().toString().contains(delivererID))
                    .collect(Collectors.toSet());
        }

        String delivererFirstName = delivererTextFieldFirstName.getText();
        if(!delivererFirstName.isEmpty()){
            initialDelivererList = initialDelivererList.stream()
                    .filter(deliverer -> deliverer.getFirstName().toLowerCase().contains(delivererFirstName.toLowerCase()))
                    .collect(Collectors.toSet());
        }

        String delivererLastName = delivererTextFieldLastName.getText();
        if(!delivererLastName.isEmpty()){
            initialDelivererList = initialDelivererList.stream()
                    .filter(deliverer -> deliverer.getLastName().toLowerCase().contains(delivererLastName.toLowerCase()))
                    .collect(Collectors.toSet());
        }

        String delivererContract = delivererTextFieldContract.getText();
        if(!delivererContract.isEmpty()){
            initialDelivererList = initialDelivererList.stream()
                    .filter(deliverer -> deliverer.getContract().getId().toString().contains(delivererContract))
                    .collect(Collectors.toSet());
        }

        String delivererBonus = delivererTextFieldBonus.getText();
        if (!delivererBonus.isEmpty()) {
            initialDelivererList = initialDelivererList.stream()
                    .filter(deliverer -> {
                        Bonus bonus = deliverer.getBonusDostavljaca();
                        return bonus.iznosBonusaNaOsnovnuPlacu().toString().contains(delivererBonus);
                    })
                    .collect(Collectors.toSet());
        }

        ObservableList<Deliverer> delivererObservableList = observableArrayList(initialDelivererList);

        SortedList<Deliverer> sortedList = new SortedList<>(delivererObservableList);

        sortedList.comparatorProperty().bind(delivererTableView.comparatorProperty());

        delivererTableView.setItems(sortedList);

    }
}
