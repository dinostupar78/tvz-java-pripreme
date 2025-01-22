package hr.javafx.restaurant.repositoryFile;

import hr.javafx.restaurant.model.Deliverer;
import hr.javafx.restaurant.model.Meal;
import hr.javafx.restaurant.model.Order;
import hr.javafx.restaurant.model.Restaurant;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Repository for managing orders in the restaurant system.
 * Provides functionality for reading orders from a file, saving orders to a file,
 * and retrieving orders based on their ID.
 * @param <T> The type of the order, extending the Order class.
 */

public class OrderFileRepository<T extends Order> extends AbstractFileRepository<T> {
    private static final String ORDERS_FILE_PATH = "dat/orders.txt";
    private static final Integer NUMBER_OF_ROWS_PER_ORDER = 5;

    public RestaurantFileRepository<Restaurant> restaurantRepository;
    public MealFileRepository<Meal> mealRepository;
    public DelivererFileRepository<Deliverer> delivererRepository;

    public OrderFileRepository(RestaurantFileRepository<Restaurant> restaurantRepository, MealFileRepository<Meal> mealRepository, DelivererFileRepository<Deliverer> delivererRepository) {
        this.restaurantRepository = restaurantRepository;
        this.mealRepository = mealRepository;
        this.delivererRepository = delivererRepository;
    }

    @Override
    public T findById(Long id) {
        return findAll().stream()
                .filter(order -> order.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Set<T> findAll() {
        Set<T> orders = new HashSet<>();
        try{
            Stream<String> stream = Files.lines(Path.of(ORDERS_FILE_PATH));
            List<String> fileRows = stream.collect(Collectors.toList());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // Adjust this pattern if needed

            for(Integer i = 0; i < (fileRows.size() / NUMBER_OF_ROWS_PER_ORDER); i++){
                Long id = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_ORDER));

                Long restaurantId = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_ORDER + 1));
                Restaurant restaurant = restaurantRepository.findById(restaurantId);

                String mealsIds = fileRows.get(i * NUMBER_OF_ROWS_PER_ORDER + 2);
                List<Meal> meals = Arrays.stream(mealsIds.split(","))
                        .map(idString -> Long.parseLong(idString))
                        .map(idLong -> mealRepository.findById(idLong))
                        .collect(Collectors.toList());

                Long delivererId = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_ORDER + 3));
                Deliverer deliverer = delivererRepository.findById(delivererId);

                String dateTimeString = fileRows.get(i * NUMBER_OF_ROWS_PER_ORDER + 4);
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

                Order order = new Order(id, restaurant, meals, deliverer, dateTime);
                orders.add((T) order);

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    @Override
    public void save(Set<T> entities) {
        try(PrintWriter writer = new PrintWriter(ORDERS_FILE_PATH)){
            for(T entity : entities){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                writer.println(entity.getId());
                writer.println(entity.getRestaurant().getId());

                int countMeals = 0;
                StringBuilder mealsIdBuilder = new StringBuilder();
                for(Meal meal : entity.getMeals()){
                    mealsIdBuilder.append(meal.getId());
                    countMeals++;
                    if(countMeals < entity.getMeals().size()){
                        mealsIdBuilder.append(",");
                    }
                }
                writer.println(mealsIdBuilder);
                writer.println(entity.getDeliverer().getId());
                String formattedDateTime = entity.getDeliveryDateAndTime().format(formatter);
                writer.println(formattedDateTime);

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to save entities to file: " + ORDERS_FILE_PATH, e);
        }
    }

    @Override
    public void save(T entity) {
        Set<T> entities = findAll();
        if(Optional.ofNullable(entity.getId()).isEmpty()){
            entity.setId(generateNewId());
        }
        entities.add(entity);
        save(entities);
    }

    private Long generateNewId(){
        return findAll().stream().map(b -> b.getId())
                .max((i1, i2) -> i1.compareTo(i2)).orElse(1l) + 1;
    }
}
