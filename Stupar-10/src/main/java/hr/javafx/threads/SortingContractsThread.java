package hr.javafx.threads;

import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.repositoryDatabase.ContractDatabaseRepository;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.util.Duration;

import java.util.List;
import java.util.stream.Collectors;

import static javafx.collections.FXCollections.observableArrayList;

public class SortingContractsThread implements Runnable{

    private ContractDatabaseRepository<Contract> contractRepository;
    private TableView<Contract> contractTableView;

    public SortingContractsThread(ContractDatabaseRepository<Contract> contractRepository, TableView<Contract> contractTableView) {
        this.contractRepository = contractRepository;
        this.contractTableView = contractTableView;
    }
    @Override
    public void run() {
        Timeline salaryTimeLine = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    try {
                        List<Contract> contracts = contractRepository.findAll().stream().toList();

                        List<Contract> sortedContracts = contracts.stream()
                                .sorted((c1, c2) -> c2.getSalary().compareTo(c1.getSalary()))
                                .collect(Collectors.toList());

                        Platform.runLater(() -> {
                            ObservableList<Contract> observableList = observableArrayList(sortedContracts);
                            contractTableView.setItems(observableList);
                        });

                    } catch (Exception f) {
                        f.printStackTrace();
                    }
                }),
                new KeyFrame(Duration.seconds(1))
        );

        salaryTimeLine.setCycleCount(Timeline.INDEFINITE);

        salaryTimeLine.play();


    }
}
