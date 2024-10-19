package hr.java.production.main;

import hr.java.restaurant.model.*;

import java.util.Scanner;

public class Main {
    private static final Integer numberOfCategories = 3;
    private static final Integer numberOfIngredients = 5;
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

    }

    public static Category categoryInput(Scanner scanner){
        System.out.println("Unesite ime kategorije: ");
        String imeKategorije = scanner.nextLine();
        System.out.println("Unesite opis kategorije: ");
        String opisKategorije = scanner.nextLine();
        //System.out.println("Podaci:\n " + imeKategorije + (" ") + opisKategorije);
        return new Category(imeKategorije, opisKategorije);
    }
}