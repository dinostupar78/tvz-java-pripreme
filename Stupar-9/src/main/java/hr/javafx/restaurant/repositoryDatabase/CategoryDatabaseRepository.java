package hr.javafx.restaurant.repositoryDatabase;

import hr.javafx.restaurant.exception.RepositoryAccessException;
import hr.javafx.restaurant.model.Category;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class CategoryDatabaseRepository<T extends Category> extends AbstractDatabaseRepository<T> {

    private Connection connectToDatabase() throws IOException, SQLException {
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
    public T findById(Long id) {
        return null;
    }

    @Override
    public Set<T> findAll() throws RepositoryAccessException {
        Set<T> categories = new HashSet<>();
        try{
            Connection connection = connectToDatabase();

            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM CATEGORY");
            while (resultSet.next()){
                Category category = extractCategoryFromResultSet(resultSet);
                categories.add((T) category);
            }

            return categories;


        }catch(IOException | SQLException e){
            throw new RepositoryAccessException(e);
        }
    }

    private static Category extractCategoryFromResultSet(ResultSet resultSet) throws SQLException{
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");

        Category category = new Category(id, name, description);
        return category;
    }

    @Override
    public void save(Set<T> entities) {
        try(Connection connection = connectToDatabase()){
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO CATEGORY(NAME, DESCRIPTION)" + " VALUES(?, ?)");

            for(T entity : entities){
                stmt.setString(1, entity.getName());
                stmt.setString(2, entity.getDescription());
                stmt.executeUpdate();
            }

        }catch (IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        }

    }

    @Override
    public void save(T entity) {
        try(Connection connection = connectToDatabase()){
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO CATEGORY(NAME, DESCRIPTION)" + " VALUES(?, ?)");
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getDescription());
            stmt.executeUpdate();

        }catch (IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        }
    }
}
