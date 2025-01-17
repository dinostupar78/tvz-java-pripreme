package hr.javafx.controller;

import hr.javafx.restaurant.model.Category;
import hr.javafx.restaurant.repository.CategoryRepository;
import hr.javafx.utils.HandleSearchClickUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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


    public void addNewCategory(){
        StringBuilder errorMessages = new StringBuilder();
        //Long id = Optional.ofNullable(1L);
        String categoryName = categoryTextFieldName.getText();
        if(categoryName.isEmpty()){
            errorMessages.append("Unos naziva kategorije je obavezan!\n");
        }
        String categoryDescription = categoryTextFieldDescription.getText();
        if(categoryDescription.isEmpty()){
            errorMessages.append("Unos deskripcije kategorije je obavezan!\n");
        }


        if(errorMessages.length() > 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pogreške kod unosa nove kategorije");
            alert.setHeaderText("Kategorija" + categoryName + " nije spremljena zbog pogreški kod unosa");
            alert.setContentText(errorMessages.toString());
            alert.showAndWait();

        } else{
            Category category = new Category(null, categoryName, categoryDescription);

            CategoryRepository<Category> categoryRepository = new CategoryRepository<>();
            categoryRepository.save(category);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uspješno spremanje nove kategorije");
            alert.setHeaderText("Kategorija" + categoryName + " je uspješno spremljena");
            StringBuilder sb = new StringBuilder();
            sb.append("Naziv kategorije: ")
                    .append(categoryName)
                    .append("\n");
            sb.append("Deskripcija kategorije: ")
                    .append(categoryDescription)
                    .append("\n");
            alert.setContentText(sb.toString());
            alert.showAndWait();

            categoryTextFieldName.clear();
            categoryTextFieldDescription.clear();

        }

    }

}
