package hr.javafx.controller;

import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Deliverer;
import hr.javafx.restaurant.repository.ContractRepository;
import hr.javafx.restaurant.repository.DelivererRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

import static javafx.collections.FXCollections.observableArrayList;

public class DelivererController {
    @FXML
    private TextField delivererTextFieldID;

    @FXML
    private TextField delivererTextFieldFirstName;

    @FXML
    private TextField delivererTextFieldLastName;

    @FXML
    private TextField delivererTextFieldContract;

    @FXML
    private TextField delivererTextFieldBonus;

    @FXML
    private TableView<Deliverer> delivererTableView;

    @FXML
    private TableColumn<Deliverer, String> delivererColumnID;

    @FXML
    private TableColumn<Deliverer, String> delivererColumnFirstName;

    @FXML
    private TableColumn<Deliverer, String> delivererColumnLastName;

    @FXML
    private TableColumn<Deliverer, String> delivererColumnContract;

    @FXML
    private TableColumn<Deliverer, String> delivererColumnBonus;

    @FXML
    private TableColumn<Deliverer, Void> delivererColumnImages;

    private ContractRepository contractRepository = new ContractRepository();
    private DelivererRepository delivererRepository = new DelivererRepository<>(contractRepository);

    public void initialize() {
        delivererColumnID.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getId()))
        );

        delivererColumnFirstName.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getFirstName()))
        );

        delivererColumnLastName.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getLastName()))
        );

        delivererColumnContract.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getContract().getContractType()))
        );

        delivererColumnBonus.setCellValueFactory(cellData -> {
            Deliverer deliverer = cellData.getValue();
            Bonus bonus = deliverer.getBonusDostavljaca();
            return new SimpleStringProperty(bonus.iznosBonusaNaOsnovnuPlacu().toString());
        });

        addImageButtonToTable();

        delivererTableView.getSortOrder().add(delivererColumnID);
    }

    private void addImageButtonToTable() {
        delivererColumnImages.setCellFactory(param -> new TableCell<>() {
            private final Button viewButton = new Button("View Images");

            {
                viewButton.setOnAction(event -> {
                    Deliverer deliverer = getTableView().getItems().get(getIndex());
                    showImageViewer(deliverer);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(viewButton);
                }
            }
        });
    }

    private void showImageViewer(Deliverer deliverer) {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Label label = new Label("Images for: " + deliverer.getFirstName() + " " + deliverer.getLastName());

        // ImageView za prikaz trenutne slike
        ImageView imageView = new ImageView();
        imageView.setFitWidth(300);  // Prilagodba širine na manju veličinu
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        // Inicijalizacija indeksa slike
        int[] currentIndex = {0};

        // Osigurati da deliverer.getImagePaths() bude modifikabilan
        if (deliverer.getImagePaths() == null) {
            deliverer.setImagePaths(new ArrayList<>());
        }

        // Ako postoji barem jedna slika, učitaj prvu
        if (!deliverer.getImagePaths().isEmpty()) {
            try {
                imageView.setImage(new Image("file:" + deliverer.getImagePaths().get(currentIndex[0])));
            } catch (Exception e) {
                System.err.println("Error loading initial image.");
            }
        }

        // Gumb za sljedeću sliku
        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> {
            if (currentIndex[0] < deliverer.getImagePaths().size() - 1) {
                currentIndex[0]++;
                try {
                    imageView.setImage(new Image("file:" + deliverer.getImagePaths().get(currentIndex[0])));
                } catch (Exception e) {
                    System.err.println("Error loading next image.");
                }
            }
        });

        // Gumb za prethodnu sliku
        Button prevButton = new Button("Previous");
        prevButton.setOnAction(event -> {
            if (currentIndex[0] > 0) {
                currentIndex[0]--;
                try {
                    imageView.setImage(new Image("file:" + deliverer.getImagePaths().get(currentIndex[0])));
                } catch (Exception e) {
                    System.err.println("Error loading previous image.");
                }
            }
        });

        // Gumb za dodavanje nove slike
        Button addImageButton = new Button("Add Image");
        addImageButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                String newImagePath = selectedFile.getAbsolutePath();

                // Provjera i pretvorba u ArrayList ako nije već
                if (!(deliverer.getImagePaths() instanceof ArrayList)) {
                    deliverer.setImagePaths(new ArrayList<>(deliverer.getImagePaths()));
                }

                deliverer.getImagePaths().add(newImagePath);

                // Prikazivanje nove slike odmah
                currentIndex[0] = deliverer.getImagePaths().size() - 1;  // Prikazuje novododanu sliku
                try {
                    imageView.setImage(new Image("file:" + newImagePath));
                } catch (Exception e) {
                    System.err.println("Error loading new image: " + newImagePath);
                }
            }
        });

        // Dodajemo komponente u VBox
        vbox.getChildren().addAll(label, imageView, prevButton, nextButton, addImageButton);

        // Postavljanje Scene
        Scene scene = new Scene(vbox, 400, 350);  // Manja širina za prikaz slika i gumba
        stage.setScene(scene);
        stage.setTitle("Image Viewer");
        stage.setResizable(true);  // Omogućavanje promjene veličine
        stage.show();
    }


    public void filterDeliverers() {
        Set<Deliverer> initialDelivererList = delivererRepository.findAll();

        String delivererID = delivererTextFieldID.getText();
        if (!delivererID.isEmpty()) {
            initialDelivererList = initialDelivererList.stream()
                    .filter(deliverer -> deliverer.getId().toString().contains(delivererID))
                    .collect(Collectors.toSet());
        }

        String delivererFirstName = delivererTextFieldFirstName.getText();
        if (!delivererFirstName.isEmpty()) {
            initialDelivererList = initialDelivererList.stream()
                    .filter(deliverer -> deliverer.getFirstName().toLowerCase().contains(delivererFirstName.toLowerCase()))
                    .collect(Collectors.toSet());
        }

        String delivererLastName = delivererTextFieldLastName.getText();
        if (!delivererLastName.isEmpty()) {
            initialDelivererList = initialDelivererList.stream()
                    .filter(deliverer -> deliverer.getLastName().toLowerCase().contains(delivererLastName.toLowerCase()))
                    .collect(Collectors.toSet());
        }

        String delivererContract = delivererTextFieldContract.getText();
        if (!delivererContract.isEmpty()) {
            initialDelivererList = initialDelivererList.stream()
                    .filter(deliverer -> deliverer.getContract().getId().toString().contains(delivererContract))
                    .collect(Collectors.toSet());
        }

        String delivererBonus = delivererTextFieldBonus.getText();
        if (!delivererBonus.isEmpty()) {
            initialDelivererList = initialDelivererList.stream()
                    .filter(deliverer -> {
                        Bonus bonus = deliverer.getBonusDostavljaca();
                        return bonus.iznosBonusaNaOsnovnuPlacu().toString().contains(delivererBonus);
                    })
                    .collect(Collectors.toSet());
        }

        ObservableList<Deliverer> delivererObservableList = observableArrayList(initialDelivererList);

        SortedList<Deliverer> sortedList = new SortedList<>(delivererObservableList);

        sortedList.comparatorProperty().bind(delivererTableView.comparatorProperty());

        delivererTableView.setItems(sortedList);
    }
}
