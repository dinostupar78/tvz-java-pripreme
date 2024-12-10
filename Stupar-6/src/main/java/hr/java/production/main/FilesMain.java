package hr.java.production.main;

import hr.java.restaurant.enums.ContractType;
import hr.java.restaurant.generics.RestaurantLabourExchangeOffice;
import hr.java.restaurant.model.*;
import hr.java.restaurant.repository.*;
import hr.java.utils.ComparatorUtils;
import hr.java.utils.LambdaUtils;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static hr.java.production.main.Main.*;
import static hr.java.utils.MealRestaurantUtils.mapCitiesToRestaurants;
import static hr.java.utils.MealRestaurantUtils.mapMealsToRestaurants;

public class FilesMain {
    public static void main(String[] args) {

        CategoryRepository<Category> categoryRepository = new CategoryRepository<>();
        IngredientRepository<Ingredient> ingredientRepository = new IngredientRepository<>(categoryRepository);
        MealsRepository<Meal> mealsRepository = new MealsRepository<>(categoryRepository);
        ContractRepository<Contract> contractRepository = new ContractRepository<>();
        ChefRepository<Chef> chefRepository = new ChefRepository<>(contractRepository);
        WaiterRepository<Waiter> waiterRepository = new WaiterRepository<>(contractRepository);
        DelivererRepository<Deliverer> delivererRepository = new DelivererRepository<>(contractRepository);
        AddressRepository<Address> addressRepository = new AddressRepository<>();
        RestaurantRepository<Restaurant> restaurantRepository = new RestaurantRepository<>(addressRepository, mealsRepository, chefRepository, waiterRepository, delivererRepository);
        OrderRepository<Order> orderRepository = new OrderRepository<>(restaurantRepository, mealsRepository, delivererRepository);


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

        Contract contract11 = new Contract(6L, new BigDecimal(3000), LocalDate.now(), LocalDate.now().plusYears(3), ContractType.FULL_TIME);
        Contract contract22 = new Contract(7L, new BigDecimal(3000), LocalDate.now(), LocalDate.now().plusYears(3), ContractType.FULL_TIME);

        Waiter de1 = new Waiter(6L, "John", "Doe", contract11, new Bonus(new BigDecimal(500)));
        Waiter de2 = new Waiter(7L, "Jane", "Smith", contract22, new Bonus(new BigDecimal(500)));

        Category mainCourse = new Category(1L, "Main Course", "Hot meals");
        Category desserts = new Category(2L, "Desserts", "Sweet dishes");

        Ingredient ingredient1 = new Ingredient(1L, "Tomato", mainCourse, new BigDecimal("0.50"), "Fresh");
        Ingredient ingredient2 = new Ingredient(2L, "Cheese", desserts, new BigDecimal("1.00"), "Grated");

        List<Ingredient> ingredients1 = new ArrayList<>();
        List<Contract> contracts1 = new ArrayList<>();
        ingredients1.add(ingredient1);
        ingredients1.add(ingredient2);


        ingredientRepository.save(ingredients1);











    }
}

