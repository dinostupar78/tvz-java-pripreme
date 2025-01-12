package hr.javafx.controller;

import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.model.Waiter;
import hr.javafx.restaurant.repository.ContractRepository;
import hr.javafx.restaurant.repository.WaiterRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.util.Set;

import static javafx.collections.FXCollections.observableArrayList;

public class WaiterAddController {
    @FXML
    private TextField waiterTextFieldFirstName;

    @FXML
    private TextField waiterTextFieldLastName;

    @FXML
    private ComboBox<Contract> waiterComboBoxContract;

    @FXML
    private TextField waiterTextFieldBonus;

    ContractRepository<Contract> contractRepository = new ContractRepository<>();
    WaiterRepository<Waiter> waiterRepository = new WaiterRepository<>(contractRepository);

    public void initialize(){
        Set<Contract> contracts = contractRepository.findAll();

        waiterComboBoxContract.setItems(observableArrayList(contracts));

        waiterComboBoxContract.setConverter(new StringConverter<Contract>() {
            @Override
            public String toString(Contract contract) {
                return contract != null ? contract.getContractType().toString() : "";
            }

            @Override
            public Contract fromString(String s) {
                return null;
            }
        });

        waiterComboBoxContract.setPromptText("Select a Contract");
    }

    public void addNewWaiter(){
        String waiterFirstName = waiterTextFieldFirstName.getText();

        String waiterLastName = waiterTextFieldLastName.getText();

        Contract selectedContract = waiterComboBoxContract.getValue();

        String waiterBonus = waiterTextFieldBonus.getText();
        BigDecimal bonusAmount = new BigDecimal(waiterBonus);
        Bonus bonus = new Bonus(bonusAmount);

        Waiter waiter = new Waiter.BuilderWaiter(null)
                .waiterFirstName(waiterFirstName)
                .waiterLastName(waiterLastName)
                .waiterContract(selectedContract)
                .waiterBonusKonobara(bonus)
                .build();

        waiterRepository.save(waiter);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uspješno spremanje novog konobara");
        alert.setHeaderText("Konobar je uspješno spremljen");
        StringBuilder sb = new StringBuilder();
        sb.append("Ime konobara: ")
                .append(waiterFirstName)
                .append("\n");
        sb.append("Prezime konobara: ")
                .append(waiterLastName)
                .append("\n");
        sb.append("Ugovor konobara: ")
                .append(selectedContract)
                .append("\n");
        sb.append("Bonus konobara: ")
                .append(bonus)
                .append("\n");
        alert.setContentText(sb.toString());
        alert.showAndWait();

        waiterTextFieldFirstName.clear();
        waiterTextFieldLastName.clear();
        waiterComboBoxContract.setValue(null);
        waiterTextFieldBonus.clear();

    }
}
