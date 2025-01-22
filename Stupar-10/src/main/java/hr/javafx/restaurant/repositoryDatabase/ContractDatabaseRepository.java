package hr.javafx.restaurant.repositoryDatabase;

import hr.javafx.restaurant.enums.ContractType;
import hr.javafx.restaurant.exception.RepositoryAccessException;
import hr.javafx.restaurant.model.Contract;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class ContractDatabaseRepository<T extends Contract> extends AbstractDatabaseRepository<T> {
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
        Set<T> contracts = new HashSet<>();
        Connection connection;

        try {
            connection = connectToDatabase();
            try (Statement stmt = connection.createStatement();
                 ResultSet resultSet = stmt.executeQuery("SELECT * FROM CONTRACT")) {
                while (resultSet.next()) {
                    Contract contract = extractContractFromResultSet(resultSet, connection);
                    contracts.add((T) contract);
                }
            }
        } catch (IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        } finally {
            disconnectFromDatabase();
        }

        return contracts;
    }

    private Contract extractContractFromResultSet(ResultSet resultSet, Connection connection) throws SQLException{
        Long id = resultSet.getLong("id");
        BigDecimal salary = resultSet.getBigDecimal("salary");
        LocalDate start_date = resultSet.getDate("start_date").toLocalDate();
        LocalDate end_date = resultSet.getDate("end_date").toLocalDate();
        String contract_type = resultSet.getString("contract_type");

        ContractType contractType = ContractType.valueOf(contract_type);

        Contract contract = new Contract(id, salary, start_date, end_date, contractType);

        return contract;
    }

    @Override
    public synchronized void save(Set<T> entities) throws RepositoryAccessException {
        try(Connection connection = connectToDatabase()){
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO CONTRACT(SALARY, START_DATE, END_DATE, CONTRACT_TYPE)" + " VALUES(?, ?, ?, ?)");
            for(T entity : entities){
                stmt.setBigDecimal(1, entity.getSalary());
                stmt.setDate(2, Date.valueOf(entity.getStartTime()));
                stmt.setDate(3, Date.valueOf(entity.getEndTime()));
                stmt.setString(4, entity.getContractType().name());
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
                    "INSERT INTO CONTRACT(SALARY, START_DATE, END_DATE, CONTRACT_TYPE)" + " VALUES(?, ?, ?, ?)");
            stmt.setBigDecimal(1, entity.getSalary());
            stmt.setDate(2, Date.valueOf(entity.getStartTime()));
            stmt.setDate(3, Date.valueOf(entity.getEndTime()));
            stmt.setString(4, entity.getContractType().name());
            stmt.executeUpdate();

        }catch (IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        } finally {
            disconnectFromDatabase();
        }
    }


}
