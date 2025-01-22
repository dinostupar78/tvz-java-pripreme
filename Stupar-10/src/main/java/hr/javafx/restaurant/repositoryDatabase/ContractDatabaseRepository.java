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
    private static Connection connectToDatabase() throws IOException, SQLException {
        Properties props = new Properties();
        props.load(new FileReader("C:\\Users\\Dino\\Desktop\\Pripreme - Java\\Lab9\\Stupar-9\\src\\main\\resources\\database.properties"));

        return DriverManager.getConnection(
                props.getProperty("databaseUrl"),
                props.getProperty("username"),
                props.getProperty("password"));

    }

    private void disconnectFromDatabase(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Set<T> findAll() throws RepositoryAccessException {
        Set<T> contracts = new HashSet<>();
        try{
            Connection connection = connectToDatabase();

            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM CONTRACT");
            while (resultSet.next()){
                Contract contract = extractContractFromResultSet(resultSet);
                contracts.add((T) contract);
            }

            return contracts;

        }catch(IOException | SQLException e){
            throw new RepositoryAccessException(e);
        }
    }

    private static Contract extractContractFromResultSet(ResultSet resultSet) throws SQLException{
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
    public void save(Set<T> entities) throws RepositoryAccessException {
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
        }
    }

    @Override
    public void save(T entity) throws RepositoryAccessException {
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
        }
    }
}
