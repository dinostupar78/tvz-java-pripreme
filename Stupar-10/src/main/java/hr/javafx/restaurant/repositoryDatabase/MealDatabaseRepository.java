package hr.javafx.restaurant.repositoryDatabase;

import hr.javafx.restaurant.exception.RepositoryAccessException;
import hr.javafx.restaurant.model.Category;
import hr.javafx.restaurant.model.Ingredient;
import hr.javafx.restaurant.model.Meal;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class MealDatabaseRepository<T extends Meal> extends AbstractDatabaseRepository<T> {
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
        Set<T> meals = new HashSet<>();
        Connection connection;
        try {
            connection = connectToDatabase();
            try (Statement stmt = connection.createStatement();
                 ResultSet resultSet = stmt.executeQuery("SELECT * FROM MEAL")) {
                while (resultSet.next()) {
                    Meal meal = extractMealFromResultSet(resultSet, connection);
                    meals.add((T) meal);
                }
            }
        }catch(IOException | SQLException e){
            throw new RepositoryAccessException(e);
        }
        return meals;
    }

    private Meal extractMealFromResultSet(ResultSet resultSet, Connection connection) throws SQLException{
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        Long category_id = resultSet.getLong("category_id");
        Set<Ingredient> ingredients = new HashSet<>();
        BigDecimal price = resultSet.getBigDecimal("price");
        Integer calories = resultSet.getInt("calories");

        Category category = getCategoryById(category_id, connection);

        Meal meal = new Meal(id, name, category, ingredients, price, calories);

        return meal;

    }

    public static Category getCategoryById(Long categoryId, Connection connection) throws SQLException{
        String query = "SELECT * FROM CATEGORY WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, categoryId);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    return new Category(id, name, description);
                } else {
                    throw new SQLException("Category not found for id: " + categoryId);
                }
            }
        }
    }

    @Override
    public synchronized void save(Set<T> entities) throws RepositoryAccessException {
        try(Connection connection = connectToDatabase()){
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO MEAL(NAME, CATEGORY_ID, PRICE, CALORIES)" + " VALUES(?, ?, ?, ?)");
            for(T entity : entities){
                stmt.setString(1, entity.getName());
                stmt.setLong(2, entity.getCategory().getId());
                stmt.setBigDecimal(3, entity.getPrice());
                stmt.setInt(4, entity.getCalories());
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
                    "INSERT INTO MEAL(NAME, CATEGORY_ID, PRICE, CALORIES)" + " VALUES(?, ?, ?, ?)");
            stmt.setString(1, entity.getName());
            stmt.setLong(2, entity.getCategory().getId());
            stmt.setBigDecimal(3, entity.getPrice());
            stmt.setInt(4, entity.getCalories());
            stmt.executeUpdate();

        }catch (IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        } finally {
            disconnectFromDatabase();
        }
    }
}
