package hr.javafx.controller;

import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Chef;
import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.repository.ChefRepository;
import hr.javafx.restaurant.repository.ContractRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.util.Set;

import static javafx.collections.FXCollections.observableArrayList;

public class ChefAddController {
    @FXML
    private TextField chefTextFieldFirstName;

    @FXML
    private TextField chefTextFieldLastName;

    @FXML
    private ComboBox<Contract> chefComboBoxContract;

    @FXML
    private TextField chefTextFieldBonus;

    ContractRepository<Contract> contractRepository = new ContractRepository<>();
    ChefRepository<Chef> chefRepository = new ChefRepository<>(contractRepository);

    public void initialize(){
        Set<Contract> contracts = contractRepository.findAll();

        chefComboBoxContract.setItems(observableArrayList(contracts));

        chefComboBoxContract.setConverter(new StringConverter<Contract>() {
            @Override
            public String toString(Contract contract) {
                return contract != null ? contract.getContractType().toString() : "";
            }

            @Override
            public Contract fromString(String s) {
                return null;
            }
        });

        chefComboBoxContract.setPromptText("Select a Contract");

    }

    public void addNewChef(){
        String chefFirstName = chefTextFieldFirstName.getText();

        String chefLastName = chefTextFieldLastName.getText();

        Contract selectedContract = chefComboBoxContract.getValue();

        String chefBonus = chefTextFieldBonus.getText();
        BigDecimal bonusAmount = new BigDecimal(chefBonus);
        Bonus bonus = new Bonus(bonusAmount);

        Chef chef = new Chef.BuilderChef(null)
                .chefFirstName(chefFirstName)
                .chefLastName(chefLastName)
                .chefContract(selectedContract)
                .chefBonusKuhara(bonus)
                .build();

        chefRepository.save(chef);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uspješno spremanje novog kuhara");
        alert.setHeaderText("Kuhar je uspješno spremljen");
        StringBuilder sb = new StringBuilder();
        sb.append("Ime kuhara: ")
                .append(chefFirstName)
                .append("\n");
        sb.append("Prezime kuhara: ")
                .append(chefLastName)
                .append("\n");
        sb.append("Ugovor kuhara: ")
                .append(selectedContract)
                .append("\n");
        sb.append("Bonus kuhara: ")
                .append(bonus)
                .append("\n");
        alert.setContentText(sb.toString());
        alert.showAndWait();

        chefTextFieldFirstName.clear();
        chefTextFieldLastName.clear();
        chefComboBoxContract.setValue(null);
        chefTextFieldBonus.clear();

    }
}
