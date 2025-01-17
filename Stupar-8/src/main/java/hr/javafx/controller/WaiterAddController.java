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
        StringBuilder errorMessages = new StringBuilder();

        String waiterFirstName = waiterTextFieldFirstName.getText();
        if(waiterFirstName.isEmpty()){
            errorMessages.append("Unos imena Dostavljača je obavezan!\n");
        }

        String waiterLastName = waiterTextFieldLastName.getText();
        if(waiterLastName.isEmpty()){
            errorMessages.append("Unos prezimena Dostavljača je obavezan!\n");
        }

        Contract selectedContract = waiterComboBoxContract.getValue();
        if (selectedContract == null) {
            errorMessages.append("Odabir ugovora je obavezan!\n");
        }

        String waiterBonus = waiterTextFieldBonus.getText();
        Bonus bonus = null;

        if (waiterBonus.isEmpty()) {
            errorMessages.append("Unos bonusa Dostavljača je obavezan!\n");
        } else {
            try {
                if (!waiterBonus.matches("^[0-9]+(\\.[0-9]{1,4})?$")) {
                    errorMessages.append("Bonus mora biti pozitivan broj, npr. 10.00!\n");
                } else {
                    BigDecimal bonusAmount = new BigDecimal(waiterBonus);
                    if (bonusAmount.compareTo(BigDecimal.ZERO) <= 0) {
                        errorMessages.append("Bonus mora biti veći od 0.00!\n");
                    } else {
                        bonus = new Bonus(bonusAmount);
                    }
                }
            } catch (NumberFormatException e) {
                errorMessages.append("Pogrešan format bonusa!\n");
            }
        }

        if (errorMessages.length() > 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pogreške kod unosa novog konobara");
            alert.setHeaderText("Konobar nije spremljen zbog pogreški kod unosa");
            alert.setContentText(errorMessages.toString());
            alert.showAndWait();
        } else{

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
}
