package hr.javafx.restaurant.repositoryDatabase;

import hr.javafx.restaurant.exception.RepositoryAccessException;
import hr.javafx.restaurant.model.Address;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class AddressDatabaseRepository<T extends Address> extends AbstractDatabaseRepository<T> {
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
    public T findById(Long id) throws RepositoryAccessException {
        return null;
    }

    @Override
    public Set<T> findAll() throws RepositoryAccessException {
        Set<T> addresses = new HashSet<>();
        try{
            Connection connection = connectToDatabase();

            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM ADDRESS");
            while (resultSet.next()){
                Address address = extractAddressFromResultSet(resultSet);
                addresses.add((T) address);
            }

            return addresses;


        }catch(IOException | SQLException e){
            throw new RepositoryAccessException(e);
        }
    }

    private static Address extractAddressFromResultSet(ResultSet resultSet) throws SQLException{
        Long id = resultSet.getLong("id");
        String street = resultSet.getString("STREET");
        String house_number = resultSet.getString("HOUSE_NUMBER");
        String city = resultSet.getString("CITY");
        String postal_code = resultSet.getString("POSTAL_CODE");

        Address address = new Address(id, street, house_number, city, postal_code);
        return address;
    }

    @Override
    public void save(Set<T> entities) throws RepositoryAccessException {
        try(Connection connection = connectToDatabase()){
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO ADDRESS(STREET, HOUSE_NUMBER, CITY, POSTAL_CODE)" + " VALUES(?, ?, ?, ?)");
            for(T entity : entities){
                stmt.setString(1, entity.getStreet());
                stmt.setString(2, entity.getHouseNumber());
                stmt.setString(3, entity.getCity());
                stmt.setString(4, entity.getPostalCode());

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
                    "INSERT INTO ADDRESS(STREET, HOUSE_NUMBER, CITY, POSTAL_CODE)" + " VALUES(?, ?, ?, ?)");

            stmt.setString(1, entity.getStreet());
            stmt.setString(2, entity.getHouseNumber());
            stmt.setString(3, entity.getCity());
            stmt.setString(4, entity.getPostalCode());

            stmt.executeUpdate();

        }catch (IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        }
    }
}
