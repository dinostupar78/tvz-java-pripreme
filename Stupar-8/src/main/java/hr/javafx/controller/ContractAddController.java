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
        StringBuilder errorMessages = new StringBuilder();

        String contractSalary = contractTextFieldSalary.getText();
        BigDecimal salary = null;
        if (contractSalary.isEmpty()) {
            errorMessages.append("Unos plaće je obavezan!\n");
        } else {
            try {
                salary = new BigDecimal(contractSalary);
                if (salary.compareTo(BigDecimal.ZERO) <= 0) {
                    errorMessages.append("Plaća mora biti pozitivan broj!\n");
                }
            } catch (NumberFormatException e) {
                errorMessages.append("Pogreška pri unosu plaće. Provjerite format!\n");
            }
        }

        LocalDate startTime = contractDatePickerStartTime.getValue();
        if (startTime == null) {
            errorMessages.append("Odabir početnog datuma ugovora je obavezan!\n");
        }

        LocalDate endTime = contractDatePickerEndTime.getValue();
        if (endTime == null) {
            errorMessages.append("Odabir završnog datuma ugovora je obavezan!\n");
        } else if (endTime.isBefore(startTime)) {
            errorMessages.append("Završni datum ugovora ne može biti prije početnog!\n");
        }

        ContractType selectedContract = contractComboBoxContractType.getValue();
        if (selectedContract == null) {
            errorMessages.append("Odabir tipa ugovora je obavezan!\n");
        }

        if (errorMessages.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pogreške kod unosa novog ugovora");
            alert.setHeaderText("Ugovor nije spremljen zbog pogreški kod unosa");
            alert.setContentText(errorMessages.toString());
            alert.showAndWait();
        } else {
            Contract contract = new Contract(null, salary, startTime, endTime, selectedContract);
            contractRepository.save(contract);

            // Show success message
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

            // Clear fields after successful save
            contractTextFieldSalary.clear();
            contractDatePickerStartTime.setValue(null);
            contractDatePickerEndTime.setValue(null);
            contractComboBoxContractType.setValue(null);
        }

    }






}
