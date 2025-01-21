package hr.javafx.controller;

import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Waiter;
import hr.javafx.restaurant.repositoryDatabase.ContractDatabaseRepository;
import hr.javafx.restaurant.repositoryDatabase.WaiterDatabaseRepository;
import hr.javafx.utils.HandleSearchClickUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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

    private Waiter selectedWaiter;

    //private ContractFileRepository contractRepository = new ContractFileRepository();
    //private WaiterFileRepository waiterRepository = new WaiterFileRepository<>(contractRepository);

    private ContractDatabaseRepository contractRepository = new ContractDatabaseRepository();
    private WaiterDatabaseRepository waiterRepository = new WaiterDatabaseRepository();

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

        waiterTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedWaiter = newValue; // Set the selected waiter
        });

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

    public void onEditClick(ActionEvent actionEvent) {
        if (selectedWaiter == null) {
            System.out.println("Please select a waiter to edit.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/javafx/waitersEdit.fxml"));
            Parent root = loader.load();

            WaiterEditController editController = loader.getController();
            editController.setWaiterData(selectedWaiter);

            Stage stage = new Stage();
            stage.setTitle("Edit Waiter");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.showAndWait();

            refreshWaiterList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshWaiterList() {
        ObservableList<Waiter> waiterObservableList = observableArrayList(waiterRepository.findAll());
        waiterTableView.setItems(waiterObservableList);
    }

    public void onDeleteClick(ActionEvent actionEvent) {
        if (selectedWaiter == null) {
            System.out.println("Please select a waiter to delete.");
            return;
        }

        // Display a confirmation dialog to the user
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Waiter");
        alert.setHeaderText("Are you sure you want to delete this waiter?");
        alert.setContentText("This action cannot be undone.");

        // Wait for the user's response
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Delete the selected waiter from the repository
                waiterRepository.delete(selectedWaiter.getId());

                // Refresh the list of waiters in the TableView
                refreshWaiterList();

                // Optionally, show a success message to the user
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Deletion Successful");
                successAlert.setHeaderText("Waiter Deleted");
                successAlert.setContentText("The selected waiter has been deleted successfully.");
                successAlert.showAndWait();
            }
        });
    }
}
