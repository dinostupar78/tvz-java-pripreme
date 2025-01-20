package hr.javafx.restaurant.repositoryDatabase;

import hr.javafx.restaurant.exception.RepositoryAccessException;
import hr.javafx.restaurant.model.*;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import static java.sql.DriverManager.getConnection;

public class OrderDatabaseRepository<T extends Order> extends AbstractDatabaseRepository<T> {
    private static Connection connectToDatabase() throws IOException, SQLException {
        Properties props = new Properties();
        props.load(new FileReader("C:\\Users\\Dino\\Desktop\\Pripreme - Java\\Lab9\\Stupar-9\\src\\main\\resources\\database.properties"));

        return getConnection(
                props.getProperty("databaseUrl"),
                props.getProperty("username"),
                props.getProperty("password"));

    }

    private void disconnectFromDatabase(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Set<T> findAll() throws RepositoryAccessException {
        Set<T> orders = new HashSet<>();
        try (Connection connection = connectToDatabase();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery("SELECT * FROM RESTAURANT_ORDER")) {

            while (resultSet.next()) {
                Order order = extractOrderFromResultSet(resultSet);
                orders.add((T) order);

            }

            return orders;

        } catch (IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        }

    }

    private Order extractOrderFromResultSet(ResultSet resultSet) throws SQLException, IOException{
        Long id = resultSet.getLong("id");
        Long restaurantId = resultSet.getLong("restaurant_id");
        Long delivererId = resultSet.getLong("deliverer_id");
        Timestamp timestamp = resultSet.getTimestamp("date_and_time");
        LocalDateTime deliveryDateAndTime = timestamp != null ? timestamp.toLocalDateTime() : null;

        Restaurant restaurant = getRestaurantById(restaurantId);
        Deliverer deliverer = findDelivererById(delivererId);

        Set<Meal> meals = getMealsForOrder(id);

        return new Order(id, restaurant, new ArrayList<>(meals), deliverer, deliveryDateAndTime);

    }

    public Set<Meal> getMealsForOrder(Long orderId) throws SQLException, IOException {
        Set<Meal> meals = new HashSet<>();
        String query = "SELECT * FROM RESTAURANT_ORDER_MEAL WHERE ID = ?";

        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setLong(1, orderId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    Long mealId = resultSet.getLong("meal_id");
                    Meal meal = RestaurantDatabaseRepository.getMealById(mealId);
                    meals.add(meal);
                }
            }
        }
        return meals;
    }



    private Restaurant getRestaurantById(Long restaurantId) throws SQLException, IOException {
        String query = "SELECT * FROM RESTAURANT WHERE ID = ?";

        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setLong(1, restaurantId);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    Long addressId = resultSet.getLong("address_id");

                    // Assuming Address fetching logic exists
                    Address address = RestaurantDatabaseRepository.getAddressById(addressId);

                    return new Restaurant(id, name, address, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
                }
            }
        }

        throw new SQLException("Restaurant not found for ID: " + restaurantId);
    }

    private Deliverer findDelivererById(Long waiterId) throws SQLException, IOException {
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
        try (Connection connection = connectToDatabase()) {
            // PreparedStatement for inserting orders into RESTAURANT_ORDER table
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO RESTAURANT_ORDER (RESTAURANT_ID, DELIVERER_ID, DATE_AND_TIME) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            for (T entity : entities) {
                stmt.setLong(1, entity.getRestaurant().getId());
                stmt.setLong(2, entity.getDeliverer().getId());
                stmt.setDate(3, Date.valueOf(entity.getDeliveryDateAndTime().toLocalDate()));
                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long orderId = generatedKeys.getLong(1);

                        // Now insert meals into the RESTAURANT_ORDER_MEAL table
                        String mealQuery = "INSERT INTO RESTAURANT_ORDER_MEAL (RESTAURANT_ORDER_ID, MEAL_ID) VALUES (?, ?)";
                        try (PreparedStatement mealStmt = connection.prepareStatement(mealQuery)) {
                            for (Meal meal : entity.getMeals()) {
                                mealStmt.setLong(1, orderId);
                                mealStmt.setLong(2, meal.getId());
                                mealStmt.executeUpdate();
                            }
                        }
                    } else {
                        throw new SQLException("Creating order failed, no ID obtained.");
                    }
                }
            }
        } catch (IOException | SQLException e) {
            throw new RepositoryAccessException("Error saving restaurant order(s)", e);
        }
    }

    @Override
    public void save(T entity) throws RepositoryAccessException {
        String query = "INSERT INTO RESTAURANT_ORDER (RESTAURANT_ID, DELIVERER_ID, DATE_AND_TIME) VALUES (?, ?, ?)";
        try (Connection connection = connectToDatabase();
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, entity.getRestaurant().getId());
            stmt.setLong(2, entity.getDeliverer().getId());
            stmt.setDate(3, java.sql.Date.valueOf(entity.getDeliveryDateAndTime().toLocalDate()));
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long orderId = generatedKeys.getLong(1);

                    String mealQuery = "INSERT INTO RESTAURANT_ORDER_MEAL (RESTAURANT_ORDER_ID, MEAL_ID) VALUES (?, ?)";
                    try (PreparedStatement mealStmt = connection.prepareStatement(mealQuery)) {
                        for (Meal meal : entity.getMeals()) {
                            mealStmt.setLong(1, orderId);
                            mealStmt.setLong(2, meal.getId());
                            mealStmt.executeUpdate();
                        }
                    }
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }

        } catch (SQLException | IOException e) {
            throw new RepositoryAccessException("Error saving restaurant order", e);
        }
    }
}
