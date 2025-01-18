package hr.javafx.controller;

import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.model.Deliverer;
import hr.javafx.restaurant.repository.ContractRepository;
import hr.javafx.restaurant.repository.DelivererRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.util.Set;

import static javafx.collections.FXCollections.observableArrayList;

public class DelivererAddController {
    @FXML
    private TextField delivererTextFieldFirstName;

    @FXML
    private TextField delivererTextFieldLastName;

    @FXML
    private ComboBox<Contract> delivererComboBoxContract;

    @FXML
    private TextField delivererTextFieldBonus;

    ContractRepository<Contract> contractRepository = new ContractRepository<>();
    DelivererRepository<Deliverer> delivererRepository = new DelivererRepository<>(contractRepository);

    public void initialize(){
        Set<Contract> contracts = contractRepository.findAll();

        delivererComboBoxContract.setItems(observableArrayList(contracts));

        delivererComboBoxContract.setConverter(new StringConverter<Contract>() {
            @Override
            public String toString(Contract contract) {
                return contract != null ? contract.getContractType().toString() : "";
            }

            @Override
            public Contract fromString(String s) {
                return null;
            }
        });

        delivererComboBoxContract.setPromptText("Select a Contract");
    }

    public void addNewDeliverer(){
        StringBuilder errorMessages = new StringBuilder();

        String delivererFirstName = delivererTextFieldFirstName.getText();
        if(delivererFirstName.isEmpty()){
            errorMessages.append("Unos imena Dostavljača je obavezan!\n");
        }

        String delivererLastName = delivererTextFieldLastName.getText();
        if(delivererLastName.isEmpty()){
            errorMessages.append("Unos prezimena Dostavljača je obavezan!\n");
        }

        Contract selectedContract = delivererComboBoxContract.getValue();
        if (selectedContract == null) {
            errorMessages.append("Odabir ugovora je obavezan!\n");
        }

        String delivererBonus = delivererTextFieldBonus.getText();
        Bonus bonus = null;
        if (delivererBonus.isEmpty()) {
            errorMessages.append("Unos bonusa Dostavljača je obavezan!\n");
        } else {
            try {
                if (!delivererBonus.matches("^[0-9]+(\\.[0-9]{1,4})?$")) {
                    errorMessages.append("Bonus mora biti pozitivan broj, npr. 10.00!\n");
                } else {
                    BigDecimal bonusAmount = new BigDecimal(delivererBonus);
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
            alert.setTitle("Pogreške kod unosa novog dostavljača");
            alert.setHeaderText("Dostavljač nije spremljen zbog pogreški kod unosa");
            alert.setContentText(errorMessages.toString());
            alert.showAndWait();
        } else{

            Deliverer deliverer = new Deliverer.BuilderDeliverer(null)
                    .delivererFirstName(delivererFirstName)
                    .delivererLastName(delivererLastName)
                    .delivererContract(selectedContract)
                    .delivererBonusDostavljaca(bonus)
                    .build();

            delivererRepository.save(deliverer);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uspješno spremanje novog dostavljača");
            alert.setHeaderText("Dostavljač je uspješno spremljen");
            StringBuilder sb = new StringBuilder();
            sb.append("Ime dostavljača: ")
                    .append(delivererFirstName)
                    .append("\n");
            sb.append("Prezime dostavljača: ")
                    .append(delivererLastName)
                    .append("\n");
            sb.append("Ugovor dostavljača: ")
                    .append(selectedContract)
                    .append("\n");
            sb.append("Bonus dostavljača: ")
                    .append(bonus)
                    .append("\n");
            alert.setContentText(sb.toString());
            alert.showAndWait();

            delivererTextFieldFirstName.clear();
            delivererTextFieldLastName.clear();
            delivererComboBoxContract.setValue(null);
            delivererTextFieldBonus.clear();

        }
    }
}
