package hr.javafx.utils;

import hr.javafx.restaurant.exception.DuplicateEntityException;
import hr.javafx.restaurant.generics.RestaurantLabourExchangeOffice;
import hr.javafx.restaurant.model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static hr.javafx.main.InputMain.log;
import static hr.javafx.utils.ExceptionUtils.checkDuplicateRestaurantData;

/**
 * Utility klasa koja pruža statičke metode za unos podataka vezanih uz restorane i narudžbe.
 */

public class RestaurantInputUtils {
    private static long restaurantIdCounter = 0;
    private static long orderIdCounter = 0;

    /**
     * Unosi podatke za restoran, uključujući ime, adresu, jela, kuhare, konobare i dostavljače.
     * Ova metoda provodi provjere unosa, uključujući provjeru duplikata imena restorana.
     * @param scanner Scanner objekt za unos podataka
     * @param addresses Array adresa koje su dostupne za restorane
     * @param meals Array jela koja su dostupna za restorane
     * @param chefs Array kuhara koji su dostupni za restorane
     * @param waiters Array konobara koji su dostupni za restorane
     * @param deliverers Array dostavljača koji su dostupni za restorane
     * @return Novi restoran s unesenim podacima
     */

    public static Restaurant restoranInput(Scanner scanner, List<Address> addresses, Set<Meal> meals, Set<Chef> chefs, Set<Waiter> waiters, Set<Deliverer> deliverers) {
        String restaurantName = inputRestaurantName(scanner);
        Address restaurantAddress = DataInputUtils.addressInput(scanner);
        Set<Meal> selecetedMeal = SelectedInputUtils.selectedMeals(scanner, meals);
        Set<Chef> selectedChef = SelectedInputUtils.selectedChefs(scanner, chefs);
        Set<Waiter> selectedWaiter = SelectedInputUtils.selectedWaiters(scanner, waiters);
        Set<Deliverer> selectedDeliverer = SelectedInputUtils.selectedDeliverers(scanner, deliverers);

        long id = restaurantIdCounter++;

        return new Restaurant(id, restaurantName, restaurantAddress, selecetedMeal, selectedChef, selectedWaiter, selectedDeliverer);

    }

    public static String inputRestaurantName(Scanner scanner) {
        Boolean isValid = false; // Change to false to start checking for valid input
        String restaurantName = "";

        while (!isValid) {  // Loop until a valid name is entered
            System.out.println("Unesite ime restorana:");
            restaurantName = scanner.nextLine();

            // Check if the name is valid
            if (restaurantName.isEmpty() || restaurantName.length() < 3) {
                log.info(Messages.INVALID_RESTAURANT_INPUT);
            } else {
                try {
                    checkDuplicateRestaurantData(restaurantName);  // Check for duplicates
                    isValid = true;  // If no exception occurs, it's valid
                } catch (DuplicateEntityException e) {
                    log.info(e.getMessage());  // Log the duplicate error
                }
            }
        }

        return restaurantName;
    }

    /**
     * Unosi podatke za narudžbu, uključujući odabir restorana, jela, dostavljača i vremena dostave.
     * Ova metoda također provodi provjere unosa i validira format vremena dostave.
     * @param scanner Scanner objekt za unos podataka
     * @param genericRestaurantList Array restorana koji su dostupni za narudžbe
     * @param meals Array jela koja su dostupna za narudžbe
     * @param deliverers Array dostavljača koji su dostupni za narudžbe
     * @return Nova narudžba s unesenim podacima
     */

    public static Order orderInput(Scanner scanner, RestaurantLabourExchangeOffice<Restaurant> genericRestaurantList, Set<Meal> meals, Set<Deliverer> deliverers) {
        Restaurant selectedRestaurant = selectRestaurant(scanner, genericRestaurantList);
        List<Meal> selectedMeals = selectMealsFromRestaurant(scanner, selectedRestaurant);
        Deliverer selectedDeliverer = selectDelivererFromRestaurant(scanner, new ArrayList<>(deliverers));
        LocalDateTime deliveryTime = inputDeliveryTime(scanner);

        long id = orderIdCounter++;
        System.out.println("Uspješna narudžba!");
        return new Order(id, selectedRestaurant, selectedMeals, selectedDeliverer, deliveryTime);
    }

    public static Restaurant selectRestaurant(Scanner scanner, RestaurantLabourExchangeOffice<Restaurant> genericRestaurantList) {
        List<Restaurant> restaurants = genericRestaurantList.getRestaurants();
        Boolean isFirstRestaurantSelected = false;
        while (true) {
            if(!isFirstRestaurantSelected){
                System.out.println("Popis restorana, odaberite jedan brojem 1-" + restaurants.size() + ":");
                for (int i = 0; i < restaurants.size(); i++) {
                    System.out.println((i + 1) + ". " + restaurants.get(i).getName());
                }
            }
            isFirstRestaurantSelected = true;
            try {

                int restaurantChoice = scanner.nextInt();
                scanner.nextLine();

                if (restaurantChoice >= 1 && restaurantChoice <= restaurants.size()) {
                    return restaurants.get(restaurantChoice - 1);
                } else {
                    log.info(Messages.INVALID_RESTAURANT_INPUT);
                }
            } catch (InputMismatchException e) {
                log.info(Messages.INVALID_RESTAURANT_INPUT);
                scanner.nextLine();
            }
        }
    }

    public static List<Meal> selectMealsFromRestaurant(Scanner scanner, Restaurant restaurant){
        List<Meal> selectedMeals = new ArrayList<>();
        List<Meal> availableMeals = new ArrayList<>(restaurant.getMeals());
        System.out.println("Popis jela za restoran " + restaurant.getName() + " (odaberite brojem, 0 za kraj):");
        while(true){
            for (int i = 0; i < availableMeals.size(); i++) {
                System.out.println((i + 1) + ". " + availableMeals.get(i).getName());
            }
            try {
                int mealChoice = scanner.nextInt();
                scanner.nextLine();

                if (mealChoice == 0)
                    break;

                if (mealChoice >= 1 && mealChoice <= availableMeals.size()) {
                    Meal meal = availableMeals.get(mealChoice - 1); // dodat poruku
                    if (!selectedMeals.contains(meal)) {
                        selectedMeals.add(meal);
                        System.out.println("Jelo " + meal.getName() + " uspješno dodano u narudžbu.");
                    } else {
                        System.out.println("Već ste odabrali ovo jelo.");
                    }
                } else {
                    log.info(Messages.INVALID_MEAL_INPUT);
                }
            } catch (InputMismatchException e) {
                log.info(Messages.INVALID_MEAL_INPUT);
                scanner.nextLine();
            }
        }
        return selectedMeals;
    }

    public static Deliverer selectDelivererFromRestaurant(Scanner scanner, List<Deliverer> deliverers){
        while (true) {
            System.out.println("Popis dostavljača, odaberite jednog brojem 1-" + deliverers.size() + ":");
            for (int i = 0; i < deliverers.size(); i++) {
                System.out.println((i + 1) + ". " + deliverers.get(i).getFirstName() + " " + deliverers.get(i).getLastName());
            }
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice >= 1 && choice <= deliverers.size()) {
                    Deliverer deliverer = deliverers.get(choice - 1);
                    deliverer.incrementDostave();
                    System.out.println("Dostavljač " + deliverer.getFirstName() + " " + deliverer.getLastName() + " uspješno dodan u narudžbu.");
                    return deliverer;
                } else {
                    System.out.println("Krivi unos, pokušajte ponovo.");
                }

            } catch (InputMismatchException e) {
                log.info(Messages.INVALID_DELIVERER_INPUT);
                scanner.nextLine();
            }
        }
    }

    public static LocalDateTime inputDeliveryTime(Scanner scanner){
        while (true) {
            System.out.println("Unesite vrijeme dostave (u formatu: yyyy-MM-dd HH:mm):");
            String input = scanner.nextLine();
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime deliveryTime = LocalDateTime.parse(input, formatter);

                if (deliveryTime.isAfter(LocalDateTime.now())) {
                    return deliveryTime;
                } else {
                    System.out.println("Vrijeme dostave mora biti u budućnosti. Pokušajte ponovo.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Neispravan format datuma i vremena. Pokušajte ponovo.");
            }
        }
    }
}
