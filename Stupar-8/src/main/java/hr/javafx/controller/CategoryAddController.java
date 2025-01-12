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
        //Long id = Optional.ofNullable(1L);
        String categoryName = categoryTextFieldName.getText();
        String categoryDescription = categoryTextFieldDescription.getText();

        Category category = new Category(null, categoryName, categoryDescription);

        CategoryRepository<Category> categoryRepository = new CategoryRepository<>();
        categoryRepository.save(category);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uspješno spremanje nove kategorije");
        alert.setHeaderText("Kategorija " + categoryName + " je uspješno spremljena");
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
