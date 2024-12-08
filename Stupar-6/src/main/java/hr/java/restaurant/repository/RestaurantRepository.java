package hr.java.restaurant.repository;

import hr.java.restaurant.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    void save(List<T> entities) {

    }
}
