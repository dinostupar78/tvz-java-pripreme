package hr.javafx.main;

import hr.javafx.restaurant.generics.RestaurantLabourExchangeOffice;
import hr.javafx.restaurant.model.*;
import hr.javafx.restaurant.repositoryFile.*;
import hr.javafx.utils.ComparatorUtils;
import hr.javafx.utils.LambdaUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import static hr.javafx.main.InputMain.*;
import static hr.javafx.utils.MealRestaurantUtils.mapCitiesToRestaurants;
import static hr.javafx.utils.MealRestaurantUtils.mapMealsToRestaurants;

public class FilesMain {
    public static void main(String[] args) {

        CategoryFileRepository<Category> categoryRepository = new CategoryFileRepository<>();
        IngredientFileRepository<Ingredient> ingredientRepository = new IngredientFileRepository<>(categoryRepository);
        MealFileRepository<Meal> mealsRepository = new MealFileRepository<>(categoryRepository);
        ContractFileRepository<Contract> contractRepository = new ContractFileRepository<>();
        ChefFileRepository<Chef> chefRepository = new ChefFileRepository<>(contractRepository);
        WaiterFileRepository<Waiter> waiterRepository = new WaiterFileRepository<>(contractRepository);
        DelivererFileRepository<Deliverer> delivererRepository = new DelivererFileRepository<>(contractRepository);
        AddressFileRepository<Address> addressRepository = new AddressFileRepository<>();
        RestaurantFileRepository<Restaurant> restaurantRepository = new RestaurantFileRepository<>(addressRepository, mealsRepository, chefRepository, waiterRepository, delivererRepository);
        OrderFileRepository<Order> orderRepository = new OrderFileRepository<>(restaurantRepository, mealsRepository, delivererRepository);


        Set<Contract> contracts = contractRepository.findAll();
        Set<Meal> meals = mealsRepository.findAll();
        Set<Restaurant> restaurants = restaurantRepository.findAll();
        Set<Chef> chefs = chefRepository.findAll();
        Set<Waiter> waiters = waiterRepository.findAll();
        Set<Deliverer> deliverers = delivererRepository.findAll();
        List<Order> orders = new ArrayList<>(orderRepository.findAll());

        List<Person> employees = new ArrayList<>();
        employees.addAll(chefs);
        employees.addAll(waiters);
        employees.addAll(deliverers);

        // Convert Set to List where necessary
        List<Restaurant> restaurantList = new ArrayList<>(restaurants); // Convert Set to List

        RestaurantLabourExchangeOffice<Restaurant> restaurantLabourExchangeOffice = new RestaurantLabourExchangeOffice<>(restaurantList);
        Map<Meal, RestaurantLabourExchangeOffice<Restaurant>> mealRestaurantMap = mapMealsToRestaurants(restaurantLabourExchangeOffice);

        Person highestPaidEmployee = findHighestPaidEmployee(employees);
        System.out.println("\nNajplaceniji zaposlenik: ");
        printEmployeeInfo(highestPaidEmployee);

        Person longestContractEmployee = findLongestContractEmployee(employees);
        System.out.println("Zaposlenik sa najdužim ugovorom: ");
        printEmployeeInfo(longestContractEmployee);

        ComparatorUtils.printHighestPaidEmployeeInRestaurants(restaurantLabourExchangeOffice);
        ComparatorUtils.printHighestEmployeedEmployeeInRestaurants(restaurantLabourExchangeOffice);

        ComparatorUtils.printMealsSortedByRestaurantCount(mealRestaurantMap);
        ComparatorUtils.printSortedIngredientsAlphabetically(meals);

        LambdaUtils.findAndPrintRestaurantWithMostEmployees(restaurantList);

        LambdaUtils.findAndPrintMostPopularMeal(mealRestaurantMap);

        //LambdaUtils.printIngredientsForAllOrders(orders);

        LambdaUtils.calculateTotalPrice(orders);

        Map<String, RestaurantLabourExchangeOffice<Restaurant>> mealRestaurantCity = mapCitiesToRestaurants(restaurantLabourExchangeOffice);
        LambdaUtils.groupRestaurantsByCity(restaurantLabourExchangeOffice);

        mealRestaurantCity.forEach((city, office) -> {
            System.out.println("\nCity: " + city);
            System.out.println("Restaurants: ");
            office.getRestaurants().forEach(restaurant -> System.out.println(restaurant));
        });

        // Contract seralization
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("contract.dat"))){
            out.writeObject(contractRepository.findAll());
            System.out.println("\nContracts serialized successfully.");
        } catch( Exception e){
            e.printStackTrace();
        }

        // Contract deseralization
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("contract.dat"))){
            contracts = (Set<Contract>) in.readObject();
            System.out.println("Contracts deserialized successfully.");
            contracts.forEach(contract -> {
                System.out.println("Contract ID: " + contract.getId() +
                        ", Salary: " + contract.getSalary() +
                        ", Start Date: " + contract.getStartTime() +
                        ", End Date: " + contract.getEndTime() +
                        ", Contract Type: " + contract.getContractType());
            });

        }catch(Exception e){
            e.printStackTrace();
        }

        // Restaurant seralization
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("restaurant.dat"))){
            out.writeObject(restaurantRepository.findAll());
            System.out.println("\nRestaurants serialized successfully.");
        }catch(Exception e){
            e.printStackTrace();
        }

        // Restaurant deseralization
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("restaurant.dat"))){
            restaurants = (Set<Restaurant>) in.readObject();
            System.out.println("Restaurants deserialized successfully.");
            restaurants.forEach(restaurant -> System.out.println("Restaurant: " + restaurant.getName()));

        }catch(Exception e){
            e.printStackTrace();
        }

        // Order seralization
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("order.dat"))){
            out.writeObject(orderRepository.findAll());
            System.out.println("\nOrders serialized successfully.");
        }catch(Exception e){
            e.printStackTrace();
        }

        // Order deseralization
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("order.dat"))){
            HashSet<Order> hashSetOrders = (HashSet<Order>) in.readObject();
            orders = new ArrayList<>(hashSetOrders);
            System.out.println("Orders deserialized successfully.");
            orders.forEach(order -> {
                System.out.println("Order ID: " + order.getId());
                System.out.println("Restaurant: " + order.getRestaurant().getName());
                System.out.println("Total Price: " + order.getTotalPrice());
            });

        }catch(Exception e){
            e.printStackTrace();
        }

    }


}

