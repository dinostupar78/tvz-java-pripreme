package hr.javafx.controller;

import hr.javafx.restaurant.enums.ContractType;
import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.model.Waiter;
import hr.javafx.restaurant.repositoryDatabase.ContractDatabaseRepository;
import hr.javafx.restaurant.repositoryDatabase.WaiterDatabaseRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class WaiterEditController {
    @FXML
    private TextField waiterTextFieldFirstName;
    @FXML
    private TextField waiterTextFieldLastName;
    @FXML
    private ComboBox<ContractType> waiterComboBoxContract;
    @FXML
    private TextField waiterTextFieldBonus;

    private Waiter currentWaiter;

    private ContractDatabaseRepository contractRepository = new ContractDatabaseRepository();
    private WaiterDatabaseRepository waiterRepository = new WaiterDatabaseRepository();

    public void setWaiterData(Waiter waiter) {
        this.currentWaiter = waiter;
        waiterTextFieldFirstName.setText(waiter.getFirstName());
        waiterTextFieldLastName.setText(waiter.getLastName());
        waiterComboBoxContract.setValue(waiter.getContract().getContractType());
        waiterTextFieldBonus.setText(String.valueOf(waiter.getBonusKonobara()));
    }

    @FXML
    private void initialize() {
        waiterComboBoxContract.setItems(FXCollections.observableArrayList(ContractType.values()));
    }

    @FXML
    private void onSaveClick(ActionEvent event) {
        currentWaiter.setFirstName(waiterTextFieldFirstName.getText());
        currentWaiter.setLastName(waiterTextFieldLastName.getText());

        ContractType selectedContractType = waiterComboBoxContract.getValue();


        BigDecimal salary = new BigDecimal(1000);

        Contract newContract = new Contract(
                currentWaiter.getContract().getId(),
                salary,
                currentWaiter.getContract().getStartTime(),
                currentWaiter.getContract().getEndTime(),
                selectedContractType
        );

        currentWaiter.setContract(newContract);

        try {
            BigDecimal bonusValue = new BigDecimal(waiterTextFieldBonus.getText()); // Convert bonus to BigDecimal
            Bonus newBonus = new Bonus(bonusValue); // Assuming Bonus constructor takes a BigDecimal
            currentWaiter.setBonusKonobara(newBonus);
        } catch (NumberFormatException e) {
            System.out.println("Invalid bonus value entered.");
            return; // Exit the method without saving if bonus is invalid
        }

        waiterRepository.update(currentWaiter);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}

