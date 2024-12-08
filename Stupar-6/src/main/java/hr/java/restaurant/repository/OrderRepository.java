package hr.java.restaurant.repository;

import hr.java.restaurant.model.Deliverer;
import hr.java.restaurant.model.Meal;
import hr.java.restaurant.model.Order;
import hr.java.restaurant.model.Restaurant;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderRepository <T extends Order> extends AbstractRepository<T>{
    private static final String ORDERS_FILE_PATH = "dat/orders.txt";
    private static final Integer NUMBER_OF_ROWS_PER_ORDER = 5;

    public RestaurantRepository<Restaurant> restaurantRepository;
    public MealsRepository<Meal> mealRepository;
    public DelivererRepository<Deliverer> delivererRepository;

    public OrderRepository(RestaurantRepository<Restaurant> restaurantRepository, MealsRepository<Meal> mealRepository, DelivererRepository<Deliverer> delivererRepository) {
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
    public List<T> findAll() {
        List<T> orders = new ArrayList<>();
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
    void save(List<T> entities) {

    }
}
