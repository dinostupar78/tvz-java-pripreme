package hr.javafx.controller;

import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.repositoryDatabase.ContractDatabaseRepository;
import hr.javafx.utils.HandleSearchClickUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

import static javafx.collections.FXCollections.observableArrayList;

public class ContractController {
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
    private TextField contractTextFieldID;

    @FXML
    private TextField contractTextFieldSalary;

    @FXML
    private DatePicker contractTextFieldStartDate;

    @FXML
    private DatePicker contractTextFieldEndDate;

    @FXML
    private TextField contractTextFieldContractType;

    @FXML
    private TableView<Contract> contractTableView;

    @FXML
    private TableColumn<Contract, String> contractColumnID;

    @FXML
    private TableColumn<Contract, String> contractColumnSalary;

    @FXML
    private TableColumn<Contract, String> contractColumnStartDate;

    @FXML
    private TableColumn<Contract, String> contractColumnEndDate;

    @FXML
    private TableColumn<Contract, String> contractColumnContractType;

    @FXML
    private TableColumn<Contract, String> c;

    //private ContractFileRepository contractRepository = new ContractFileRepository();
    private ContractDatabaseRepository contractRepository = new ContractDatabaseRepository();

    public void initialize() {
        contractColumnID.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getId()))
        );

        contractColumnSalary.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getSalary()))
                );

        contractColumnStartDate.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getStartTime()))
                );

        contractColumnEndDate.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getEndTime()))
        );

        contractColumnContractType.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getContractType()))
                );

        contractTableView.getSortOrder().add(contractColumnID);

    }

    public void filterContracts() {
        Set<Contract> initialContractList = contractRepository.findAll();

        String contractID = contractTextFieldID.getText();
        if (!contractID.isEmpty()) {
            initialContractList = initialContractList.stream()
                    .filter(contract -> contract.getId().toString().contains(contractID))
                    .collect(Collectors.toSet());
        }

        String contractSalary = contractTextFieldSalary.getText();
        if(!contractSalary.isEmpty()){
            initialContractList = initialContractList.stream()
                    .filter(contract -> contract.getSalary().toString().contains(contractSalary))
                    .collect(Collectors.toSet());
        }

        if (contractTextFieldStartDate.getValue() != null) {
            String contractStartDate = contractTextFieldStartDate.getValue()
                    .format(DateTimeFormatter.ISO_LOCAL_DATE); // Format to a standard string
            initialContractList = initialContractList.stream()
                    .filter(contract -> contract.getStartTime().toString().contains(contractStartDate))
                    .collect(Collectors.toSet());
        }

        // Filter by End Date
        if (contractTextFieldEndDate.getValue() != null) {
            String contractEndDate = contractTextFieldEndDate.getValue()
                    .format(DateTimeFormatter.ISO_LOCAL_DATE); // Format to a standard string
            initialContractList = initialContractList.stream()
                    .filter(contract -> contract.getEndTime().toString().contains(contractEndDate))
                    .collect(Collectors.toSet());
        }

        String contractContractType = contractTextFieldContractType.getText();
        if(!contractContractType.isEmpty()){
            initialContractList = initialContractList.stream()
                    .filter(contract -> contract.getContractType().toString().contains(contractContractType))
                    .collect(Collectors.toSet());
        }

        ObservableList<Contract> contractObservableList = observableArrayList(initialContractList);

        SortedList<Contract> sortedList = new SortedList<>(contractObservableList);

        sortedList.comparatorProperty().bind(contractTableView.comparatorProperty());

        contractTableView.setItems(sortedList);
    }

}
