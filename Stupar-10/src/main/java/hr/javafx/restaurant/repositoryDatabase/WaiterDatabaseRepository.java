package hr.javafx.restaurant.repositoryDatabase;

import hr.javafx.restaurant.enums.ContractType;
import hr.javafx.restaurant.exception.RepositoryAccessException;
import hr.javafx.restaurant.model.Bonus;
import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.model.Waiter;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class WaiterDatabaseRepository<T extends Waiter> extends AbstractDatabaseRepository<T> {
    private Boolean activeConnectionWithDatabase = false;

    private synchronized Connection connectToDatabase() throws IOException, SQLException {
        while (activeConnectionWithDatabase) {
            try {
                wait();
            }  catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        activeConnectionWithDatabase = true;

        Properties props = new Properties();
        props.load(new FileReader("C:\\Users\\Dino\\Desktop\\Pripreme - Java\\Lab9\\Stupar-9\\src\\main\\resources\\database.properties"));

        return DriverManager.getConnection(
                props.getProperty("databaseUrl"),
                props.getProperty("username"),
                props.getProperty("password"));

    }

    private synchronized void disconnectFromDatabase() throws RepositoryAccessException{
        activeConnectionWithDatabase = false;
        notifyAll();
    }

    @Override
    public synchronized Set<T> findAll() throws RepositoryAccessException {
        Set<T> waiters = new HashSet<>();
        Connection connection;

        try {
            connection = connectToDatabase();
            try (Statement stmt = connection.createStatement();
                 ResultSet resultSet = stmt.executeQuery("SELECT * FROM WAITER")) {
                while (resultSet.next()) {
                    Waiter waiter = extractWaiterFromResultSet(resultSet, connection);
                    waiters.add((T) waiter);
                }
            }
        } catch (IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        } finally {
            disconnectFromDatabase();
        }

        return waiters;
    }

    private static Waiter extractWaiterFromResultSet(ResultSet resultSet, Connection connection) throws SQLException{
        Long id = resultSet.getLong("id");
        String first_name = resultSet.getString("first_name");
        String last_name = resultSet.getString("last_name");
        Long contract_id = resultSet.getLong("contract_id");
        BigDecimal bonus = resultSet.getBigDecimal("bonus");

        Bonus chefBonus = new Bonus(bonus);

        Contract contract = getContractById(contract_id, connection);

        Waiter waiter = new Waiter(id, first_name, last_name, contract, chefBonus);

        return waiter;
    }

    public static Contract getContractById(Long contractId, Connection connection) throws SQLException {
        String query = "SELECT * FROM CONTRACT WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, contractId);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    BigDecimal salary = resultSet.getBigDecimal("salary");
                    LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
                    LocalDate endDate = resultSet.getDate("end_date").toLocalDate();
                    String contractType = resultSet.getString("contract_type");

                    return new Contract(id, salary, startDate, endDate, ContractType.valueOf(contractType));
                } else {
                    throw new SQLException("Contract not found for id: " + contractId);
                }
            }
        }
    }

    @Override
    public synchronized void save(Set<T> entities) throws RepositoryAccessException {
        try(Connection connection = connectToDatabase()){
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO WAITER(FIRST_NAME, LAST_NAME, CONTRACT_ID, BONUS)" + " VALUES(?, ?, ?, ?)");
            for(T entity : entities){
                stmt.setString(1, entity.getFirstName());
                stmt.setString(2, entity.getLastName());
                stmt.setLong(3, entity.getContract().getId());
                stmt.setBigDecimal(4, entity.getBonusKonobara().iznosBonusaNaOsnovnuPlacu());
                stmt.executeUpdate();
            }

        }catch (IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        } finally {
            disconnectFromDatabase();
        }

    }

    @Override
    public synchronized void save(T entity) throws RepositoryAccessException {
        try(Connection connection = connectToDatabase()){
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO WAITER(FIRST_NAME, LAST_NAME, CONTRACT_ID, BONUS)" + " VALUES(?, ?, ?, ?)");
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setLong(3, entity.getContract().getId());
            stmt.setBigDecimal(4, entity.getBonusKonobara().iznosBonusaNaOsnovnuPlacu());
            stmt.executeUpdate();

        }catch (IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        } finally {
            disconnectFromDatabase();
        }

    }

    public void update(T entity) throws RepositoryAccessException {
        String query = "UPDATE WAITER SET FIRST_NAME = ?, LAST_NAME = ?, CONTRACT_ID = ?, BONUS = ? WHERE ID = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setLong(3, entity.getContract().getId());
            stmt.setBigDecimal(4, entity.getBonusKonobara().iznosBonusaNaOsnovnuPlacu());
            stmt.setLong(5, entity.getId());
            stmt.executeUpdate();

        } catch (IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        }
    }

    public void delete(Long id) throws RepositoryAccessException {
        try (Connection connection = connectToDatabase()) {
            connection.setAutoCommit(false);

            try {
                String deleteRestaurantWaiterQuery = "DELETE FROM RESTAURANT_WAITER WHERE WAITER_ID = ?";
                try (PreparedStatement stmt = connection.prepareStatement(deleteRestaurantWaiterQuery)) {
                    stmt.setLong(1, id);
                    stmt.executeUpdate();
                }

                String deleteWaiterQuery = "DELETE FROM WAITER WHERE ID = ?";
                try (PreparedStatement stmt = connection.prepareStatement(deleteWaiterQuery)) {
                    stmt.setLong(1, id);
                    stmt.executeUpdate();
                }

                String deleteContractQuery = "DELETE FROM CONTRACT WHERE ID = (SELECT CONTRACT_ID FROM WAITER WHERE ID = ?)";
                try (PreparedStatement stmt = connection.prepareStatement(deleteContractQuery)) {
                    stmt.setLong(1, id);
                    stmt.executeUpdate();
                }

            } catch (SQLException e) {
                connection.rollback();
                throw new RepositoryAccessException(e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        }
    }
}
