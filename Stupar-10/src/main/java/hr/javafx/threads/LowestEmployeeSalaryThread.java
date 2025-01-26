package hr.javafx.threads;

import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.repositoryDatabase.ContractDatabaseRepository;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import static java.sql.DriverManager.getConnection;

public class LowestEmployeeSalaryThread implements Runnable{
    private final ContractDatabaseRepository contractRepository;
    private final Stage stage;
    private final String section;

    public LowestEmployeeSalaryThread(ContractDatabaseRepository contractRepository, Stage stage, String section) {
        this.contractRepository = contractRepository;
        this.stage = stage;
        this.section = section;
    }

    private Connection connectToDatabase() throws IOException, SQLException {
        Properties props = new Properties();
        props.load(new FileReader("C:\\Users\\Dino\\Desktop\\Pripreme - Java\\Lab9\\Stupar-9\\src\\main\\resources\\database.properties"));

        return getConnection(
                props.getProperty("databaseUrl"),
                props.getProperty("username"),
                props.getProperty("password"));
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Wait for 10 seconds before checking again
                Thread.sleep(10000);

                System.out.println("Thread started...");
                Set<Contract> contracts = contractRepository.findAll();
                if (contracts.isEmpty()) {
                    throw new RuntimeException("No contracts found");
                }

                // Find the contract with the lowest salary
                Optional<Contract> lowestSalaryContract = contracts.stream()
                        .min(Comparator.comparing(Contract::getSalary));

                if (lowestSalaryContract.isPresent()) {
                    try (Connection connection = connectToDatabase()) {
                        if (section.equals("Chef")) {
                            PreparedStatement chefStmt = connection.prepareStatement("SELECT * FROM RESTAURANT_CHEF WHERE contract_id = ?");
                            chefStmt.setLong(1, lowestSalaryContract.get().getId());
                            ResultSet chefResultSet = chefStmt.executeQuery();
                            if (chefResultSet.next()) {
                                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM CHEF WHERE ID = ?");
                                stmt.setLong(1, chefResultSet.getLong("chef_id"));
                                ResultSet chef = stmt.executeQuery();
                                if (chef.next()) {
                                    String firstName = chef.getString("first_name");
                                    String lastName = chef.getString("last_name");

                                    BigDecimal salary = lowestSalaryContract.get().getSalary();

                                    String message = String.format("Lowest Salary Chef: %s %s (Salary: %.2f)", firstName, lastName, salary);
                                    showDialog(message);
                                } else {
                                    System.out.println("No chef found for contract.");
                                }
                            } else {
                                System.out.println("No restaurant chef found for contract.");
                            }
                        }


                        if (section.equals("Waiter")) {
                            PreparedStatement waiterStmt = connection.prepareStatement("SELECT * FROM RESTAURANT_WAITER WHERE contract_id = ?");
                            waiterStmt.setLong(1, lowestSalaryContract.get().getId());
                            ResultSet waiterResultSet = waiterStmt.executeQuery();
                            if (waiterResultSet.next()) {
                                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM WAITER WHERE id = ?");
                                stmt.setLong(1, waiterResultSet.getLong("waiter_id"));
                                ResultSet waiter = stmt.executeQuery();
                                if (waiter.next()) {
                                    String firstName = waiter.getString("first_name");
                                    String lastName = waiter.getString("last_name");

                                    BigDecimal salary = lowestSalaryContract.get().getSalary();

                                    String message = String.format("Lowest Salary Waiter: %s %s (Salary: %.2f)", firstName, lastName, salary);
                                    showDialog(message);
                                }
                            }
                        }

                        if (section.equals("Deliverer")) {
                            PreparedStatement delivererStmt = connection.prepareStatement("SELECT * FROM RESTAURANT_DELIVERER WHERE contract_id = ?");
                            delivererStmt.setLong(1, lowestSalaryContract.get().getId());
                            ResultSet delivererResultSet = delivererStmt.executeQuery();
                            if (delivererResultSet.next()) {
                                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM DELIVERER WHERE id = ?");
                                stmt.setLong(1, delivererResultSet.getLong("deliverer_id"));
                                ResultSet deliverer = stmt.executeQuery();
                                if (deliverer.next()) {
                                    String firstName = deliverer.getString("first_name");
                                    String lastName = deliverer.getString("last_name");

                                    BigDecimal salary = lowestSalaryContract.get().getSalary();

                                    String message = String.format("Lowest Salary Deliverer: %s %s (Salary: %.2f)", firstName, lastName, salary);
                                    showDialog(message);
                                }
                            }
                        }

                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void showDialog(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Employee with Lowest Salary");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
