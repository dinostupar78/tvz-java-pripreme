package hr.javafx.controller;

import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.repository.ContractRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

public class ContractController {
    public void handleSearchClickContracts(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hr/javafx/mealsSearch.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            MenuItem menuItem = (MenuItem) event.getSource();
            Stage stage = (Stage) ((MenuItem) menuItem).getParentPopup().getOwnerWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Log and debug any issues with loading the FXML
        }
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

    private ContractRepository contractRepository = new ContractRepository();

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

        ObservableList<Contract> contractObservableList = javafx.collections.FXCollections.observableArrayList(initialContractList);
        contractTableView.setItems(contractObservableList);

    }



}
