package hr.javafx.controller;

import hr.javafx.restaurant.model.Category;
import hr.javafx.restaurant.repository.CategoryRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class CategoryController {

    @FXML
    private TextField categoryTextFieldID;

    @FXML
    private TextField categoryTextFieldName;

    @FXML
    private TextField categoryTextFieldDescription;

    @FXML
    private TableView<Category> categoryTableView;

    @FXML
    private TableColumn<Category, String> categoryColumnID;

    @FXML
    private TableColumn<Category, String> categoryColumnName;

    @FXML
    private TableColumn<Category, String> categoryColumnDescription;

    private CategoryRepository categoryRepository = new CategoryRepository();

    public void initialize() {
        /*categoryColumnName.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Category, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Category, String> categoryStringCellDataFeatures) {
                        return new SimpleStringProperty(categoryStringCellDataFeatures.getValue().getName());
                    }
                }
        );*/

        categoryColumnID.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getId()))
                );

        categoryColumnName.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getName()))
                );

        categoryColumnDescription.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getDescription()))
                );

    }

    public void filterCategories() {
        // Get all categories from the repository
        Set<Category> initialCategoryList = categoryRepository.findAll();


        String categoryID = categoryTextFieldID.getText();
        if (!categoryID.isEmpty()) {
            initialCategoryList = initialCategoryList.stream()
                    .filter(category -> category.getId().toString().contains(categoryID))
                    .collect(Collectors.toSet());
        }

        String categoryName = categoryTextFieldName.getText();
        if (!categoryName.isEmpty()) {
            initialCategoryList = initialCategoryList.stream()
                    .filter(category -> category.getName().toLowerCase().contains(categoryName.toLowerCase()))
                    .collect(Collectors.toSet());
        }

        String categoryDescription = categoryTextFieldDescription.getText();
        if (!categoryDescription.isEmpty()) {
            initialCategoryList = initialCategoryList.stream()
                    .filter(category -> category.getDescription().toLowerCase().contains(categoryDescription.toLowerCase()))
                    .collect(Collectors.toSet());
        }

        Set<Category> categoryList = categoryRepository.findAll();

        // Convert filtered Set to ObservableList
        ObservableList<Category> categoryObservableList = FXCollections.observableArrayList(initialCategoryList);

        // Set the items to the TableView
        categoryTableView.setItems(categoryObservableList);
    }

    public void handleSearchClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hr/javafx/categoriesSearch.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
