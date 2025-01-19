package hr.javafx.controller;

import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Waiter;
import hr.javafx.restaurant.repositoryFile.ContractFileRepository;
import hr.javafx.restaurant.repositoryFile.WaiterFileRepository;
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

public class WaiterController {
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
    private TextField waiterTextFieldID;

    @FXML
    private TextField waiterTextFieldFirstName;

    @FXML
    private TextField waiterTextFieldLastName;

    @FXML
    private TextField waiterTextFieldContract;

    @FXML
    private TextField waiterTextFieldBonus;

    @FXML
    private TableView<Waiter> waiterTableView;

    @FXML
    private TableColumn<Waiter, String> waiterColumnID;

    @FXML
    private TableColumn<Waiter, String> waiterColumnFirstName;

    @FXML
    private TableColumn<Waiter, String> waiterColumnLastName;

    @FXML
    private TableColumn<Waiter, String> waiterColumnContract;

    @FXML
    private TableColumn<Waiter, String> waiterColumnBonus;

    private ContractFileRepository contractRepository = new ContractFileRepository();
    private WaiterFileRepository waiterRepository = new WaiterFileRepository<>(contractRepository);

    public void initialize(){
        waiterColumnID.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getId()))
        );

        waiterColumnFirstName.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getFirstName()))
        );

        waiterColumnLastName.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getLastName()))
        );

        waiterColumnContract.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getContract().getContractType()))
        );

        waiterColumnBonus.setCellValueFactory(cellData -> {
            Waiter waiter = cellData.getValue();
            Bonus bonus = waiter.getBonusKonobara();
            return new SimpleStringProperty(bonus.iznosBonusaNaOsnovnuPlacu().toString());
        });

        waiterTableView.getSortOrder().add(waiterColumnID);
    }

    public void filterWaiters(){
        Set<Waiter> initialWaiterList = waiterRepository.findAll();

        String waiterID = waiterTextFieldID.getText();
        if(!waiterID.isEmpty()){
            initialWaiterList = initialWaiterList.stream()
                    .filter(waiter -> waiter.getId().toString().contains(waiterID))
                    .collect(Collectors.toSet());
        }

        String waiterFirstName = waiterTextFieldFirstName.getText();
        if(!waiterFirstName.isEmpty()){
            initialWaiterList = initialWaiterList.stream()
                    .filter(waiter -> waiter.getFirstName().toLowerCase().contains(waiterFirstName.toLowerCase()))
                    .collect(Collectors.toSet());
        }

        String waiterLastName = waiterTextFieldLastName.getText();
        if(!waiterLastName.isEmpty()){
            initialWaiterList = initialWaiterList.stream()
                    .filter(waiter -> waiter.getLastName().toLowerCase().contains(waiterLastName.toLowerCase()))
                    .collect(Collectors.toSet());
        }

        String waiterContract = waiterTextFieldContract.getText();
        if(!waiterContract.isEmpty()){
            initialWaiterList = initialWaiterList.stream()
                    .filter(waiter -> waiter.getContract().getId().toString().contains(waiterContract))
                    .collect(Collectors.toSet());
        }

        String waiterBonus = waiterTextFieldBonus.getText();
        if (!waiterBonus.isEmpty()) {
            initialWaiterList = initialWaiterList.stream()
                    .filter(waiter -> {
                        Bonus bonus = waiter.getBonusKonobara();
                        return bonus.iznosBonusaNaOsnovnuPlacu().toString().contains(waiterBonus);
                    })
                    .collect(Collectors.toSet());
        }

        ObservableList<Waiter> waiterObservableList = observableArrayList(initialWaiterList);

        SortedList<Waiter> sortedList = new SortedList<>(waiterObservableList);

        sortedList.comparatorProperty().bind(waiterTableView.comparatorProperty());

        waiterTableView.setItems(sortedList);

    }
}
