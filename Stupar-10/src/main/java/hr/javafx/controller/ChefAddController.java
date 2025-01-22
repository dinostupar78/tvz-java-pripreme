package hr.javafx.controller;

import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Chef;
import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.repositoryDatabase.ChefDatabaseRepository;
import hr.javafx.restaurant.repositoryDatabase.ContractDatabaseRepository;
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

    //ContractFileRepository<Contract> contractRepository = new ContractFileRepository<>();
    //ChefFileRepository<Chef> chefRepository = new ChefFileRepository<>(contractRepository);

    private ContractDatabaseRepository<Contract> contractRepository = new ContractDatabaseRepository();
    private ChefDatabaseRepository<Chef> chefRepository = new ChefDatabaseRepository();

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
        StringBuilder errorMessages = new StringBuilder();

        String chefFirstName = chefTextFieldFirstName.getText();
        if(chefFirstName.isEmpty()){
            errorMessages.append("Unos imena Dostavljača je obavezan!\n");
        }

        String chefLastName = chefTextFieldLastName.getText();
        if(chefLastName.isEmpty()){
            errorMessages.append("Unos prezimena Dostavljača je obavezan!\n");
        }

        Contract selectedContract = chefComboBoxContract.getValue();
        if (selectedContract == null) {
            errorMessages.append("Odabir ugovora je obavezan!\n");
        }

        String chefBonus = chefTextFieldBonus.getText();
        Bonus bonus = null;
        if (chefBonus.isEmpty()) {
            errorMessages.append("Unos bonusa Dostavljača je obavezan!\n");
        } else {
            try {
                if (!chefBonus.matches("^[0-9]+(\\.[0-9]{1,4})?$")) {
                    errorMessages.append("Bonus mora biti pozitivan broj, npr. 10.00!\n");
                } else {
                    BigDecimal bonusAmount = new BigDecimal(chefBonus);
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
            alert.setTitle("Pogreške kod unosa novog kuhara");
            alert.setHeaderText("Kuhar nije spremljen zbog pogreški kod unosa");
            alert.setContentText(errorMessages.toString());
            alert.showAndWait();
        } else{
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
}
