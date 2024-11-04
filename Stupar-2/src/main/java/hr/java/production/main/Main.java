package hr.java.production.main;

import hr.java.restaurant.model.*;
import hr.java.utils.DataUtils;
import hr.java.utils.EmployeeUtils;
import hr.java.utils.RestaurantUtils;

import java.util.Scanner;

import static hr.java.utils.RestaurantUtils.restoranInput;

public class Main {
    private static final Integer numberOfCategories = 3;
    private static final Integer numberOfIngredients = 3;
    private static final Integer numberOfMeals = 3;
    private static final Integer numberOfChefs = 3;
    private static final Integer numberOfWaiters = 3;
    private static final Integer numberOfDeliverers = 3;
    private static final Integer numberOfRestaurants = 3;
    private static final Integer restaurantAddress = 3;
    private static final Integer numberOfOrders = 3;

    public static void main(String[] args) {
        Category[] categories = new Category[numberOfCategories];
        Ingredient[] ingredients = new Ingredient[numberOfIngredients];
        Meal[] meals = new Meal[numberOfMeals];
        Chef[] chefs = new Chef[numberOfChefs];
        Waiter[] waiters = new Waiter[numberOfWaiters];
        Deliverer[] deliverers = new Deliverer[numberOfDeliverers];
        Restaurant[] restaurants = new Restaurant[numberOfRestaurants];
        Address[] addresses = new Address[restaurantAddress];
        Order[] orderers = new Order[numberOfOrders];

        Scanner scanner = new Scanner(System.in);

        for(int i = 0; i < categories.length; i++){
            System.out.println("Unesite podatke za " + (i + 1) + " kategoriju");
            Category kategorija = categoryInput(scanner);
            categories[i] = kategorija;
        }

        for(int i = 0; i < ingredients.length; i++){
            System.out.println("Unesite podatke za " + (i + 1) + " sastojak");
            Ingredient sastojak = ingredientInput(scanner, categories);
            ingredients[i] = sastojak;
        }

        for(int i = 0; i < meals.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " jelo");
            Meal jela = mealsInput(scanner, categories, ingredients);
            meals[i] = jela;
        }

        for(int i = 0; i < chefs.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " kuhara");
            Chef kuhara = chefInput(scanner);
            chefs[i] = kuhara;
        }

        for(int i = 0; i < waiters.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " konobara");
            Waiter konobar = waiterInput(scanner);
            waiters[i] = konobar;
        }

        for(int i = 0; i < deliverers.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " dostavljača");
            Deliverer dostavljac = delivererInput(scanner);
            deliverers[i] = dostavljac;
        }

        for(int i = 0; i < restaurants.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " restoran");
            Restaurant restoran = restoranInput(scanner, addresses, meals, chefs, waiters, deliverers);
            restaurants[i] = restoran;
        }

        for(int i = 0; i < orderers.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " narudžbu");
            Order order = orderInput(scanner, restaurants, meals, deliverers);
            orderers[i] = order;
        }


    }

    public static Category categoryInput(Scanner scanner) {
        return DataUtils.categoryInput(scanner);
    }

    public static Ingredient ingredientInput(Scanner scanner, Category[] categories) {
        return DataUtils.ingredientInput(scanner, categories);
    }

    public static Meal mealsInput(Scanner scanner, Category[] categories, Ingredient[] ingredients){
        return DataUtils.mealsInput(scanner, categories, ingredients);
    }

    public static Chef chefInput(Scanner scanner){
       return EmployeeUtils.chefInput(scanner);
    }

    public static Waiter waiterInput(Scanner scanner){
        return EmployeeUtils.waiterInput(scanner);
    }

    public static Deliverer delivererInput(Scanner scanner){
        return EmployeeUtils.delivererInput(scanner);
    }

    public static Address addressInput(Scanner scanner){
       return DataUtils.addressInput(scanner);
    }

    public static Restaurant restaurantInput(Scanner scanner, Address[] addresses, Meal[] meals, Chef[] chefs, Waiter[] waiters, Deliverer[] deliverers){
        return restoranInput(scanner, addresses, meals, chefs, waiters, deliverers);
    }

    public static Order orderInput(Scanner scanner, Restaurant[] restaurants, Meal[] meals, Deliverer[] deliverers) {
        return RestaurantUtils.orderInput(scanner, restaurants, meals, deliverers);
    }

}
