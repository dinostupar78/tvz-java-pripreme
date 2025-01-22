package hr.javafx.restaurant.repositoryDatabase;

import hr.javafx.restaurant.exception.RepositoryAccessException;
import hr.javafx.restaurant.model.*;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class RestaurantDatabaseRepository<T extends Restaurant> extends AbstractDatabaseRepository<T> {
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
        Set<T> restaurants = new HashSet<>();
        try (Connection connection = connectToDatabase();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery("SELECT * FROM RESTAURANT")) {

            while (resultSet.next()) {
                Restaurant restaurant = extractRestaurantFromResultSet(resultSet);
                restaurants.add((T) restaurant);
            }

            return restaurants;
        } catch (IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        }
    }

    public Restaurant extractRestaurantFromResultSet(ResultSet resultSet) throws SQLException, IOException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        Long addressId = resultSet.getLong("address_id");

        Address address = getAddressById(addressId);

        Set<Meal> meals = getMealsForRestaurant(id);
        Set<Chef> chefs = getChefsForRestaurant(id);
        Set<Waiter> waiters = getWaitersForRestaurant(id);
        Set<Deliverer> deliverers = getDeliverersForRestaurant(id);

        return new Restaurant(id, name, address, meals, chefs, waiters, deliverers);
    }

    public static Address getAddressById(Long addressId) throws SQLException, IOException {
        String query = "SELECT * FROM ADDRESS WHERE ID = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setLong(1, addressId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String street = resultSet.getString("street");
                    String house_number = resultSet.getString("house_number");
                    String city = resultSet.getString("city");
                    String postal_code = resultSet.getString("city");
                    return new Address(id, street, house_number, city, postal_code);
                } else {
                    throw new SQLException("Address not found for ID: " + addressId);
                }
            }
        }
    }

    public Set<Meal> getMealsForRestaurant(Long restaurantId) throws SQLException, IOException {
        Set<Meal> meals = new HashSet<>();
        String query = "SELECT * FROM RESTAURANT_MEAL WHERE RESTAURANT_ID = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setLong(1, restaurantId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    Long mealId = resultSet.getLong("meal_id");
                    Meal meal = getMealById(mealId);
                    meals.add(meal);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return meals;
    }

    public static Meal getMealById(Long mealId) throws SQLException, IOException {
        String query = "SELECT * FROM MEAL WHERE ID = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setLong(1, mealId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    Long category_id = resultSet.getLong("category_id");
                    Set<Ingredient> ingredients = new HashSet<>();
                    BigDecimal price = resultSet.getBigDecimal("price");
                    Integer calories = resultSet.getInt("calories");

                    Category category = MealDatabaseRepository.getCategoryById(category_id);

                    return new Meal(id, name, category, ingredients, price, calories);
                } else {
                    throw new SQLException("Meal not found for ID: " + mealId);
                }
            }
        }
    }

    private Set<Chef> getChefsForRestaurant(Long restaurantId) throws SQLException {
        Set<Chef> chefs = new HashSet<>();
        String query = "SELECT * FROM RESTAURANT_CHEF WHERE RESTAURANT_ID = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setLong(1, restaurantId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    Long chefId = resultSet.getLong("chef_id");
                    Chef chef = getChefById(chefId);
                    chefs.add(chef);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return chefs;
    }

    private Chef getChefById(Long chefId) throws SQLException, IOException {
        String query = "SELECT * FROM CHEF WHERE ID = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setLong(1, chefId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    Long contractId = resultSet.getLong("contract_id");
                    BigDecimal bonus = resultSet.getBigDecimal("bonus");

                    Contract contract = ChefDatabaseRepository.getContractById(contractId);
                    return new Chef(id, firstName, lastName, contract, new Bonus(bonus));
                } else {
                    throw new SQLException("Chef not found for ID: " + chefId);
                }
            }
        }
    }

    private Set<Waiter> getWaitersForRestaurant(Long restaurantId) throws SQLException {
        Set<Waiter> waiters = new HashSet<>();
        String query = "SELECT * FROM RESTAURANT_WAITER WHERE RESTAURANT_ID = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setLong(1, restaurantId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    Long waiterId = resultSet.getLong("waiter_id");
                    Waiter waiter = getWaiterById(waiterId);
                    waiters.add(waiter);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return waiters;
    }

    private Waiter getWaiterById(Long waiterId) throws SQLException, IOException {
        String query = "SELECT * FROM WAITER WHERE ID = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setLong(1, waiterId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    Long contractId = resultSet.getLong("contract_id");
                    BigDecimal bonus = resultSet.getBigDecimal("bonus");

                    Contract contract = ChefDatabaseRepository.getContractById(contractId);
                    return new Waiter(id, firstName, lastName, contract, new Bonus(bonus));
                } else {
                    throw new SQLException("Chef not found for ID: " + waiterId);
                }
            }
        }
    }

    private Set<Deliverer> getDeliverersForRestaurant(Long restaurantId) throws SQLException {
        Set<Deliverer> deliverers = new HashSet<>();
        String query = "SELECT * FROM RESTAURANT_DELIVERER WHERE RESTAURANT_ID = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setLong(1, restaurantId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    Long delivererId = resultSet.getLong("deliverer_id");
                    Deliverer deliverer = getDelivererById(delivererId);
                    deliverers.add(deliverer);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return deliverers;
    }

    private Deliverer getDelivererById(Long waiterId) throws SQLException, IOException {
        String query = "SELECT * FROM DELIVERER WHERE ID = ?";
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setLong(1, waiterId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    Long contractId = resultSet.getLong("contract_id");
                    BigDecimal bonus = resultSet.getBigDecimal("bonus");

                    Contract contract = ChefDatabaseRepository.getContractById(contractId);
                    return new Deliverer(id, firstName, lastName, contract, new Bonus(bonus));
                } else {
                    throw new SQLException("Chef not found for ID: " + waiterId);
                }
            }
        }
    }

    @Override
    public void save(Set<T> entities) throws RepositoryAccessException {
        try(Connection connection = connectToDatabase()){
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO RESTAURANT (NAME, ADDRESS_ID) VALUES (?, ?)");
            for(T entity : entities){
                stmt.setString(1, entity.getName());
                stmt.setLong(2, entity.getAddress().getId());
                stmt.executeUpdate();

                saveRestaurantMeals(entity);
                saveRestaurantChefs(entity);
                saveRestaurantWaiters(entity);
                saveRestaurantDeliverers(entity);
            }

        }catch (IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        }

    }

    @Override
    public void save(T entity) throws RepositoryAccessException {
        try(Connection connection = connectToDatabase()){
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO RESTAURANT (NAME, ADDRESS_ID) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, entity.getName());
            stmt.setLong(2, entity.getAddress().getId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long restaurantId = generatedKeys.getLong(1);
                    entity.setId(restaurantId);
                } else {
                    throw new SQLException("Failed to retrieve generated ID for restaurant.");
                }
            }

            saveRestaurantMeals(entity);
            saveRestaurantChefs(entity);
            saveRestaurantWaiters(entity);
            saveRestaurantDeliverers(entity);

        }catch (IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        }
    }

    private void saveRestaurantMeals(T entity) throws SQLException, IOException {
        String sql = "INSERT INTO RESTAURANT_MEAL (RESTAURANT_ID, MEAL_ID) VALUES (?, ?)";
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            for (Meal meal : entity.getMeals()) {
                stmt.setLong(1, entity.getId());
                stmt.setLong(2, meal.getId());
                stmt.executeUpdate();
            }
        }
    }

    private void saveRestaurantChefs(T entity) throws SQLException, IOException {
        String sql = "INSERT INTO RESTAURANT_CHEF (RESTAURANT_ID, CHEF_ID, CONTRACT_ID) VALUES (?, ?, ?)";
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            for (Chef chef : entity.getChefs()) {
                stmt.setLong(1, entity.getId());
                stmt.setLong(2, chef.getId());
                stmt.setLong(3, chef.getContract().getId());
                stmt.executeUpdate();
            }
        }
    }

    private void saveRestaurantWaiters(T entity) throws SQLException {
        String query = "INSERT INTO RESTAURANT_WAITER (RESTAURANT_ID, WAITER_ID, CONTRACT_ID) VALUES (?, ?, ?)";

        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            for (Waiter waiter : entity.getWaiters()) {
                stmt.setLong(1, entity.getId());
                stmt.setLong(2, waiter.getId());
                stmt.setLong(3, waiter.getContract().getId());
                stmt.executeUpdate();
            }


        } catch (SQLException | IOException e) {
            throw new SQLException("Error saving restaurant-waiter relationships", e);
        }
    }

    private void saveRestaurantDeliverers(T entity) throws SQLException {
        String query = "INSERT INTO RESTAURANT_DELIVERER (RESTAURANT_ID, DELIVERER_ID, CONTRACT_ID) VALUES (?, ?, ?)";

        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            for (Deliverer deliverer : entity.getDeliverers()) {
                stmt.setLong(1, entity.getId());
                stmt.setLong(2, deliverer.getId());
                stmt.setLong(3, deliverer.getContract().getId());
                stmt.executeUpdate();
            }

        } catch (SQLException | IOException e) {
            throw new SQLException("Error saving restaurant-deliverer relationships", e);
        }
    }



}
