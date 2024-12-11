package hr.javafx.controller;

import hr.javafx.restaurant.model.Category;
import hr.javafx.restaurant.repository.CategoryRepository;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Set;

public class CategoryController {
    @FXML
    private TextField categoryChoiceBoxID;

    @FXML
    private TextField categoryTextFieldName;

    @FXML
    private TextField categoryTextFieldDescription;

    @FXML
    private TableView<Category> categoryTableView;

    @FXML
    private TableColumn<Category, Long> categoryTableColumnID;

    @FXML
    private TableColumn<Category, String> categoryTableColumnName;

    @FXML
    private TableColumn<Category, String> categoryTableColumnDescription;

    private CategoryRepository categoryRepository = new CategoryRepository();

    public void initialize() {

    }

    public void filterCategories() {
        Set<Category> initialCategoriesList = categoryRepository.findAll();

        Long categoryID = Long.parseLong(categoryChoiceBoxID.getText());
        String categoryName = categoryTextFieldName.getText();
        String categoryDescription = categoryTextFieldDescription.getText();

        initialCategoriesList.stream()
                .filter(category -> category.getId().equals(categoryChoiceBoxID))
                .filter(category -> category.getName().equals(categoryTextFieldName))
                .filter(category -> category.getDescription().equals(categoryTextFieldDescription))
                .forEach(category -> categoryTableView.getItems().add(category));
    }

}
