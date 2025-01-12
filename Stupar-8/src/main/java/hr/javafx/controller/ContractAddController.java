package hr.javafx.controller;

import hr.javafx.restaurant.enums.ContractType;
import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.repository.ContractRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ContractAddController {
    @FXML
    private TextField contractTextFieldSalary;

    @FXML
    private DatePicker contractDatePickerStartTime;

    @FXML
    private DatePicker contractDatePickerEndTime;

    @FXML
    private ComboBox<ContractType> contractComboBoxContractType;

    ContractRepository<Contract> contractRepository = new ContractRepository<>();

    public void initialize(){
        contractComboBoxContractType.getItems().addAll(ContractType.values());
    }

    public void addNewContract(){
        String contractSalary = contractTextFieldSalary.getText();
        BigDecimal salary = new BigDecimal(contractSalary);

        String contractStartTime = String.valueOf(contractDatePickerStartTime.getValue());
        LocalDate startTime = LocalDate.parse(contractStartTime);

        String contractEndTime = String.valueOf(contractDatePickerEndTime.getValue());
        LocalDate endTime = LocalDate.parse(contractEndTime);

        ContractType selectedContract = contractComboBoxContractType.getValue();

        Contract contract = new Contract(null, salary, startTime, endTime, selectedContract);
        contractRepository.save(contract);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uspješno spremanje novog ugovora");
        alert.setHeaderText("Ugovor je uspješno spremljen");
        StringBuilder sb = new StringBuilder();
        sb.append("Plaća ugovora: ")
                .append(salary)
                .append("\n");
        sb.append("Početak ugovora: ")
                .append(startTime)
                .append("\n");
        sb.append("Završetak ugovora: ")
                .append(endTime)
                .append("\n");
        alert.setContentText(sb.toString());
        alert.showAndWait();

        contractTextFieldSalary.clear();
        contractDatePickerStartTime.setValue(null);
        contractDatePickerEndTime.setValue(null);
        contractComboBoxContractType.setValue(null);

    }






}
