package hr.javafx.controller;

import hr.javafx.restaurant.model.Category;
import hr.javafx.restaurant.repository.CategoryRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Set;

public class CategoryExportController {
    @FXML
    private Button exportButton;

    @FXML
    private TextArea exportTextArea;

    private CategoryRepository<Category> categoryRepository = new CategoryRepository<>();

    /**
     * This method is called when the export button is clicked.
     * It exports all categories to a text file and displays the content in a TextArea.
     */
    public void exportCategories() {
        // Fetch all categories from the repository
        Set<Category> categories = categoryRepository.findAll();
        StringBuilder content = new StringBuilder();

        // Append each category's string representation to the content
        for (Category category : categories) {
            content.append(category.toString()).append("\n");
        }

        // Serialize the content to a text file
        try (PrintWriter writer = new PrintWriter("categories_export.txt")) {
            writer.write(content.toString());
        } catch (FileNotFoundException e) {
            // Handle the exception by showing an error message
            showError("Error exporting categories", "Unable to create or write to the file.");
            return;
        }

        // Display the content in the TextArea
        exportTextArea.setText(content.toString());

        // Show a success message to the user
        showSuccess("Categories exported successfully", "The categories have been successfully exported to categories_export.txt.");
    }

    /**
     * This method shows an error alert with the given title and message.
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * This method shows a success alert with the given title and message.
     */
    private void showSuccess(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
