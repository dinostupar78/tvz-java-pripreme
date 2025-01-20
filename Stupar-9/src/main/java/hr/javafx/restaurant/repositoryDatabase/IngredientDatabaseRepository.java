package hr.javafx.restaurant.repositoryDatabase;

import hr.javafx.restaurant.exception.RepositoryAccessException;
import hr.javafx.restaurant.model.Category;
import hr.javafx.restaurant.model.Ingredient;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class IngredientDatabaseRepository<T extends Ingredient> extends AbstractDatabaseRepository<T> {

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
        Set<T> ingredients = new HashSet<>();
        try{
            Connection connection = connectToDatabase();

            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM INGREDIENT");
            while (resultSet.next()){
                Ingredient ingredient = extractIngredientFromResultSet(resultSet);
                ingredients.add((T) ingredient);
            }

            return ingredients;


        }catch(IOException | SQLException e){
            throw new RepositoryAccessException(e);
        }
    }

    private static Ingredient extractIngredientFromResultSet(ResultSet resultSet) throws SQLException{
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        Long category_id = resultSet.getLong("category_id");
        BigDecimal kcal = resultSet.getBigDecimal("kcal");
        String preparation_method = resultSet.getString("preparation_method");

        Category category = getCategoryById(category_id);

        Ingredient ingredient = new Ingredient(id, name, category, kcal, preparation_method);

        return ingredient;

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
                    "INSERT INTO INGREDIENT(NAME, CATEGORY_ID, KCAL, PREPARATION_METHOD)" + " VALUES(?, ?, ?, ?)");
            for(T entity : entities){
                stmt.setString(1, entity.getName());
                stmt.setString(2, entity.getCategory().getName());
                stmt.setBigDecimal(3, entity.getKcal());
                stmt.setString(4, entity.getPreparationMethod());
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
                    "INSERT INTO INGREDIENT(NAME, CATEGORY_ID, KCAL, PREPARATION_METHOD)" + " VALUES(?, ?, ?, ?)");
            stmt.setString(1, entity.getName());
            stmt.setLong(2, entity.getCategory().getId());
            stmt.setBigDecimal(3, entity.getKcal());
            stmt.setString(4, entity.getPreparationMethod());
            stmt.executeUpdate();

        }catch (IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        }

    }
}
