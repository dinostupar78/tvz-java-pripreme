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
        String delivererFirstName = delivererTextFieldFirstName.getText();

        String delivererLastName = delivererTextFieldLastName.getText();

        Contract selectedContract = delivererComboBoxContract.getValue();

        String delivererBonus = delivererTextFieldBonus.getText();
        BigDecimal bonusAmount = new BigDecimal(delivererBonus);
        Bonus bonus = new Bonus(bonusAmount);

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
