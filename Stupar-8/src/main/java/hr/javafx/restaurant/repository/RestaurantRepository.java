package hr.javafx.restaurant.repository;

import hr.javafx.restaurant.model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Repository class responsible for managing restaurant entities.
 * Provides methods for saving restaurants to a file and reading restaurants from a file.
 * @param <T> the type of restaurant that extends the {@link Restaurant} class
 */

public class RestaurantRepository <T extends Restaurant> extends AbstractRepository<T>{
    private static final String RESTAURANTS_FILE_PATH = "dat/restaurants.txt";
    private static final Integer NUMBER_OF_ROWS_PER_RESTAURANTS = 7;

    public AddressRepository<Address> addressRepository;
    public MealsRepository<Meal> mealRepository;
    public ChefRepository<Chef> chefRepository;
    public WaiterRepository<Waiter> waiterRepository;
    public DelivererRepository<Deliverer> delivererRepository;

    public RestaurantRepository(AddressRepository<Address> addressRepository, MealsRepository<Meal> mealsRepository, ChefRepository<Chef> chefRepository, WaiterRepository<Waiter> waiterRepository, DelivererRepository<Deliverer> delivererRepository) {
        this.addressRepository = addressRepository;
        this.mealRepository = mealsRepository;
        this.chefRepository = chefRepository;
        this.waiterRepository = waiterRepository;
        this.delivererRepository = delivererRepository;
    }

    @Override
    public T findById(Long id) {
        return findAll().stream()
                .filter(restaurant -> restaurant.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Set<T> findAll() {
        Set<T> restaurants = new HashSet<>();

        try{
            Stream<String> stream = Files.lines(Path.of(RESTAURANTS_FILE_PATH));
            List<String> fileRows = stream.collect(Collectors.toList());

            for(Integer i = 0; i < (fileRows.size() / NUMBER_OF_ROWS_PER_RESTAURANTS); i++){
                Long id = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_RESTAURANTS));
                String name = fileRows.get(i * NUMBER_OF_ROWS_PER_RESTAURANTS + 1);

                Long addressId = Long.parseLong(fileRows.get(i * NUMBER_OF_ROWS_PER_RESTAURANTS + 2));
                Address address = addressRepository.findById(addressId);

                String mealsIds = fileRows.get(i * NUMBER_OF_ROWS_PER_RESTAURANTS + 3);
                Set<Meal> meals = Arrays.stream(mealsIds.split(","))
                        .map(idString -> Long.parseLong(idString))
                        .map(idLong -> mealRepository.findById(idLong))
                        .collect(Collectors.toSet());

                String chefsIds = fileRows.get(i * NUMBER_OF_ROWS_PER_RESTAURANTS + 4);
                Set<Chef> chefs = Arrays.stream(chefsIds.split(","))
                        .map(idString -> Long.parseLong(idString))
                        .map(idLong -> chefRepository.findById(idLong))
                        .collect(Collectors.toSet());

                String waitersIds = fileRows.get(i * NUMBER_OF_ROWS_PER_RESTAURANTS + 5);
                Set<Waiter> waiters = Arrays.stream(waitersIds.split(","))
                        .map(idString -> Long.parseLong(idString))
                        .map(idLong -> waiterRepository.findById(idLong))
                        .collect(Collectors.toSet());

                String deliverersIds = fileRows.get(i * NUMBER_OF_ROWS_PER_RESTAURANTS + 6);
                Set<Deliverer> deliverers = Arrays.stream(deliverersIds.split(","))
                        .map(idString -> Long.parseLong(idString))
                        .map(idLong -> delivererRepository.findById(idLong))
                        .collect(Collectors.toSet());

                Restaurant restaurant = new Restaurant(id, name, address, meals, chefs, waiters, deliverers);
                restaurants.add((T) restaurant);

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return restaurants;
    }

    @Override
    public void save(Set<T> entities) {
        try(PrintWriter writer = new PrintWriter(RESTAURANTS_FILE_PATH)){
            for(T entity : entities){
                writer.println(entity.getId());
                writer.println(entity.getName());
                writer.println(entity.getAddress().getId());

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

                int countChefs = 0;
                StringBuilder chefsIdBuilder = new StringBuilder();
                for(Chef chef : entity.getChefs()){
                    chefsIdBuilder.append(chef.getId());
                    countChefs++;
                    if(countChefs < entity.getChefs().size()){
                        chefsIdBuilder.append(",");
                    }
                }
                writer.println(chefsIdBuilder);

                int countWaiters = 0;
                StringBuilder waitersIdBuilder = new StringBuilder();
                for(Waiter waiter : entity.getWaiters()){
                    waitersIdBuilder.append(waiter.getId());
                    countWaiters++;
                    if(countWaiters < entity.getWaiters().size()){
                        waitersIdBuilder.append(",");
                    }
                }
                writer.println(waitersIdBuilder);

                int countDeliverers = 0;
                StringBuilder deliverersIdBuilder = new StringBuilder();
                for(Deliverer deliverer : entity.getDeliverers()){
                    deliverersIdBuilder.append(deliverer.getId());
                    countDeliverers++;
                    if(countDeliverers < entity.getDeliverers().size()){
                        deliverersIdBuilder.append(",");
                    }
                }
                writer.println(deliverersIdBuilder);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to save entities to file: " + RESTAURANTS_FILE_PATH, e);
        }
    }

    @Override
    public void save(T entity) {

    }
}
