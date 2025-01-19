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
        Set<T> meals = new HashSet<>();
        try{
            Connection connection = connectToDatabase();

            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM MEAL");
            while (resultSet.next()){
                Meal meal = extractMealFromResultSet(resultSet);
                meals.add((T) meal);
            }

            return meals;

        }catch(IOException | SQLException e){
            throw new RepositoryAccessException(e);
        }
    }

    private static Meal extractMealFromResultSet(ResultSet resultSet) throws SQLException{
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        Long category_id = resultSet.getLong("category_id");
        Set<Ingredient> ingredients = new HashSet<>();
        BigDecimal price = resultSet.getBigDecimal("price");
        Integer calories = resultSet.getInt("calories");

        Category category = getCategoryById(category_id);

        Meal meal = new Meal(id, name, category, ingredients, price, calories);

        return meal;

    }

    private static Category getCategoryById(Long categoryId) throws SQLException{
        String query = "SELECT * FROM CATEGORY WHERE id = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(query)) {
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
            }catch(SQLException e){
                throw new RepositoryAccessException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Set<T> entities) throws RepositoryAccessException {
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
        }

    }

    @Override
    public void save(T entity) throws RepositoryAccessException {
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
        }
    }
}
