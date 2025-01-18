package hr.javafx.controller;

import hr.javafx.restaurant.model.Category;
import hr.javafx.restaurant.repository.CategoryRepository;
import hr.javafx.utils.HandleSearchClickUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class CategoryAddController {
    public void onSearchCategoryClick(ActionEvent event) {
        HandleSearchClickUtils searchClickUtils = new HandleSearchClickUtils();
        searchClickUtils.handleSearchClickCategories(event);
    }

    @FXML
    private TextField categoryTextFieldName;

    @FXML
    private TextField categoryTextFieldDescription;

    @FXML
    private CheckBox activeCheckBox;

    @FXML
    private RadioButton typeRadioButton1;

    @FXML
    private RadioButton typeRadioButton2;


    public void addNewCategory() {
        StringBuilder errorMessages = new StringBuilder();

        String categoryName = categoryTextFieldName.getText();
        if (categoryName.isEmpty()) {
            errorMessages.append("Unos naziva kategorije je obavezan!\n");
        }

        String categoryDescription = categoryTextFieldDescription.getText();
        if (categoryDescription.isEmpty()) {
            errorMessages.append("Unos deskripcije kategorije je obavezan!\n");
        }

        if (errorMessages.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pogreške kod unosa nove kategorije");
            alert.setHeaderText("Kategorija " + categoryName + " nije spremljena zbog pogreški kod unosa");
            alert.setContentText(errorMessages.toString());
            alert.showAndWait();
        } else {
            boolean isActive = activeCheckBox.isSelected();
            String categoryType = typeRadioButton1.isSelected() ? "Type1" : "Type2"; // Example for 2 category types

            // Ensure that categoryType is not null or empty
            if (categoryType == null || categoryType.trim().isEmpty()) {
                errorMessages.append("Odabir tipa kategorije je obavezan!\n");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Pogreške kod unosa nove kategorije");
                alert.setHeaderText("Kategorija " + categoryName + " nije spremljena zbog pogreški kod unosa");
                alert.setContentText(errorMessages.toString());
                alert.showAndWait();
                return;
            }

            Category category = new Category(null, categoryName, categoryDescription, isActive, categoryType);

            CategoryRepository<Category> categoryRepository = new CategoryRepository<>();
            categoryRepository.save(category);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uspješno spremanje nove kategorije");
            alert.setHeaderText("Kategorija " + categoryName + " je uspješno spremljena");
            alert.setContentText("Naziv kategorije: " + categoryName + "\n" + "Deskripcija kategorije: " + categoryDescription);
            alert.showAndWait();

            categoryTextFieldName.clear();
            categoryTextFieldDescription.clear();
            activeCheckBox.setSelected(false);
            typeRadioButton1.setSelected(false);
            typeRadioButton2.setSelected(false);
        }
    }


}
