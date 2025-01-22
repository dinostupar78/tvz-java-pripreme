package hr.javafx.threads;

import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.repositoryDatabase.ContractDatabaseRepository;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.stream.Collectors;

import static javafx.collections.FXCollections.observableArrayList;

public class SortingContractsThread implements Runnable{

    private ContractDatabaseRepository<Contract> contractRepository;
    private final TableView<Contract> contractTableView;

    public SortingContractsThread(ContractDatabaseRepository<Contract> contractRepository, TableView<Contract> contractTableView) {
        this.contractRepository = contractRepository;
        this.contractTableView = contractTableView;
    }
    @Override
    public void run() {
        try{
            List<Contract> contracts = contractRepository.findAll().stream().toList();

            List<Contract> sortedContracts = contracts.stream()
                    .sorted((c1, c2) -> c2.getSalary().compareTo(c1.getSalary()))
                    .collect(Collectors.toList());

            Platform.runLater(() -> {
                ObservableList<Contract> observableList = observableArrayList(sortedContracts);
                contractTableView.setItems(observableList);
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
