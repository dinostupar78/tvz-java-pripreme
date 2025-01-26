package hr.javafx.threads;

import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.repositoryDatabase.ContractDatabaseRepository;
import javafx.application.Platform;
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
import java.util.function.Consumer;

import static java.sql.DriverManager.getConnection;

public class HighestEmployeeSalaryThread implements Runnable{
    private final ContractDatabaseRepository contractRepository;
    private final Consumer<String> updateTitleCallback;
    private final Stage stage;
    private final String section;

    public HighestEmployeeSalaryThread(ContractDatabaseRepository contractRepository, Consumer<String> updateTitleCallback, Stage stage, String section) {
        this.contractRepository = contractRepository;
        this.updateTitleCallback = updateTitleCallback;
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
        System.out.println("Thread started...");
        Set<Contract> contracts = contractRepository.findAll();
        if(contracts.isEmpty()){
            throw new RuntimeException("No contracts found");
        }
        Optional<Contract> highestSalaryContract = contracts.stream()
                .max(Comparator.comparing(Contract::getSalary));
        try(Connection connection = connectToDatabase()){

            if (section.equals("Chef")){
                PreparedStatement chefStmt = connection.prepareStatement("SELECT * FROM RESTAURANT_CHEF WHERE contract_id = ?");
                chefStmt.setLong(1, highestSalaryContract.get().getId());
                ResultSet chefResultSet = chefStmt.executeQuery();
                if (chefResultSet.next()) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM CHEF WHERE ID = ?");
                    stmt.setLong(1, chefResultSet.getLong("chef_id"));
                    ResultSet chef = stmt.executeQuery();
                    if (chef.next()) {
                        String firstName = chef.getString("first_name");
                        String lastName = chef.getString("last_name");

                        BigDecimal salary = highestSalaryContract.get().getSalary();

                        String title = String.format("Highest Salary Chef: %s %s (Salary: %.2f)", firstName, lastName, salary);

                        Platform.runLater(() -> {
                            updateTitleCallback.accept(title);
                        });
                    }
                }
            }

            if(section.equals("Waiter")){
                PreparedStatement waiterStmt = connection.prepareStatement("SELECT * FROM RESTAURANT_WAITER WHERE contract_id = ?");
                waiterStmt.setLong(1, highestSalaryContract.get().getId());
                ResultSet waiterResultSet = waiterStmt.executeQuery();
                if(waiterResultSet.next()){
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM WAITER WHERE id = ?");
                    stmt.setLong(1, waiterResultSet.getLong("waiter_id"));
                    ResultSet waiter = stmt.executeQuery();
                    if(waiter.next()){
                        String firstName = waiter.getString("first_name");
                        String lastName = waiter.getString("last_name");

                        BigDecimal salary = highestSalaryContract.get().getSalary();

                        String title = String.format("Highest Salary Waiter: %s %s (Salary: %.2f)", firstName, lastName, salary);

                        Platform.runLater(() -> {
                            updateTitleCallback.accept(title);
                        });
                    }
                }
            }

            if(section.equals("Deliverer")){
                PreparedStatement delivererStmt = connection.prepareStatement("SELECT * FROM RESTAURANT_DELIVERER WHERE contract_id = ?");
                delivererStmt.setLong(1, highestSalaryContract.get().getId());
                ResultSet delivererResultSet = delivererStmt.executeQuery();
                if(delivererResultSet.next()){
                    PreparedStatement stmt = connection.prepareStatement("SELECT * FROM DELIVERER WHERE id = ?");
                    stmt.setLong(1, delivererResultSet.getLong("deliverer_id"));
                    ResultSet deliverer = stmt.executeQuery();
                    if(deliverer.next()){
                        String firstName = deliverer.getString("first_name");
                        String lastName = deliverer.getString("last_name");

                        BigDecimal salary = highestSalaryContract.get().getSalary();

                        String title = String.format("Highest Salary Deliverer: %s %s (Salary: %.2f)", firstName, lastName, salary);

                        Platform.runLater(() -> {
                            updateTitleCallback.accept(title);
                        });
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
