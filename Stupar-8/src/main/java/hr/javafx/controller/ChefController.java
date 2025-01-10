package hr.javafx.controller;

import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Chef;
import hr.javafx.restaurant.repository.ChefRepository;
import hr.javafx.restaurant.repository.ContractRepository;
import hr.javafx.utils.HandleSearchClickUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Set;
import java.util.stream.Collectors;

public class ChefController {
    public void onSearchCategoryClick(ActionEvent event) {
        HandleSearchClickUtils searchClickUtils = new HandleSearchClickUtils();
        searchClickUtils.handleSearchClickCategories(event);
    }

    public void onSearchIngredientClick(ActionEvent event) {
        HandleSearchClickUtils searchClickUtils = new HandleSearchClickUtils();
        searchClickUtils.handleSearchClickIngredients(event);
    }

    public void onSearchMealClick(ActionEvent event) {
        HandleSearchClickUtils searchClickUtils = new HandleSearchClickUtils();
        searchClickUtils.handleSearchClickMeals(event);
    }

    public void onSearchContractClick(ActionEvent event) {
        HandleSearchClickUtils searchClickUtils = new HandleSearchClickUtils();
        searchClickUtils.handleSearchClickContracts(event);
    }

    public void onSearchChefClick(ActionEvent event) {
        HandleSearchClickUtils searchClickUtils = new HandleSearchClickUtils();
        searchClickUtils.handleSearchClickChefs(event);
    }

    public void onSearchWaiterClick(ActionEvent event) {
        HandleSearchClickUtils searchClickUtils = new HandleSearchClickUtils();
        searchClickUtils.handleSearchClickWaiters(event);
    }

    public void onSearchDelivererClick(ActionEvent event) {
        HandleSearchClickUtils searchClickUtils = new HandleSearchClickUtils();
        searchClickUtils.handleSearchClickDeliverers(event);
    }

    public void onSearchRestaurantClick(ActionEvent event) {
        HandleSearchClickUtils searchClickUtils = new HandleSearchClickUtils();
        searchClickUtils.handleSearchClickRestaurants(event);
    }

    public void onSearchOrderClick(ActionEvent event) {
        HandleSearchClickUtils searchClickUtils = new HandleSearchClickUtils();
        searchClickUtils.handleSearchClickOrders(event);
    }

    @FXML
    private TextField chefTextFieldID;

    @FXML
    private TextField chefTextFieldFirstName;

    @FXML
    private TextField chefTextFieldLastName;

    @FXML
    private TextField chefTextFieldContract;

    @FXML
    private TextField chefTextFieldBonus;

    @FXML
    private TableView<Chef> chefTableView;

    @FXML
    private TableColumn<Chef, String> chefColumnID;

    @FXML
    private TableColumn<Chef, String> chefColumnFirstName;

    @FXML
    private TableColumn<Chef, String> chefColumnLastName;

    @FXML
    private TableColumn<Chef, String> chefColumnContract;

    @FXML
    private TableColumn<Chef, String> chefColumnBonus;

    private ContractRepository contractRepository = new ContractRepository();
    private ChefRepository chefRepository = new ChefRepository<>(contractRepository);

    public void initialize(){
        chefColumnID.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getId()))
        );

        chefColumnFirstName.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getFirstName()))
        );

        chefColumnLastName.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getLastName()))
        );

        chefColumnContract.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getValue().getContract().getContractType()))
        );

        chefColumnBonus.setCellValueFactory(cellData -> {
            Chef chef = cellData.getValue();
            Bonus bonus = chef.getBonusKuhara();
            return new SimpleStringProperty(bonus.iznosBonusaNaOsnovnuPlacu().toString());
        });
    }

    public void filterChefs(){
        Set<Chef> initialChefList = chefRepository.findAll();

        String chefID = chefTextFieldID.getText();
        if(!chefID.isEmpty()){
            initialChefList = initialChefList.stream()
                    .filter(chef -> chef.getId().toString().contains(chefID))
                    .collect(Collectors.toSet());
        }

        String chefFirstName = chefTextFieldFirstName.getText();
        if(!chefFirstName.isEmpty()){
            initialChefList = initialChefList.stream()
                    .filter(chef -> chef.getFirstName().toLowerCase().contains(chefFirstName.toLowerCase()))
                    .collect(Collectors.toSet());
        }

        String chefLastName = chefTextFieldLastName.getText();
        if(!chefLastName.isEmpty()){
            initialChefList = initialChefList.stream()
                    .filter(chef -> chef.getLastName().toLowerCase().contains(chefLastName.toLowerCase()))
                    .collect(Collectors.toSet());
        }

        String chefContract = chefTextFieldContract.getText();
        if(!chefContract.isEmpty()){
            initialChefList = initialChefList.stream()
                    .filter(chef -> chef.getContract().getId().toString().contains(chefContract))
                    .collect(Collectors.toSet());
        }

        String chefBonus = chefTextFieldBonus.getText();
        if (!chefBonus.isEmpty()) {
            initialChefList = initialChefList.stream()
                    .filter(chef -> {
                        Bonus bonus = chef.getBonusKuhara();
                        return bonus.iznosBonusaNaOsnovnuPlacu().toString().contains(chefBonus);
                    })
                    .collect(Collectors.toSet());
        }

        javafx.collections.ObservableList<Chef> chefObservableList = javafx.collections.FXCollections.observableArrayList(initialChefList);
        chefTableView.setItems(chefObservableList);

    }


}
