package hr.java.utils;

import hr.java.restaurant.exception.DuplicateEntityException;
import hr.java.restaurant.model.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import static hr.java.production.main.Main.log;
import static hr.java.utils.ExceptionUtils.checkDuplicateRestaurantData;

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

    public static Restaurant restoranInput(Scanner scanner, Address[] addresses, Meal[] meals, Chef[] chefs, Waiter[] waiters, Deliverer[] deliverers) {
        Address restaurantAddress = null;
        String restaurantName;
        int delivererNumber = 0, waiterNumber = 0, chefNumber = 0, mealNumber = 0;
        Boolean isValid = false;

        do{
            isValid = true;
            System.out.println("Unesite ime restorana: ");
            restaurantName = scanner.nextLine();
            if (restaurantName.isEmpty() || restaurantName.length() < 3) {
                log.info(Messages.INVALID_RESTAURANT_INPUT);
                isValid = false;
            } else {
                try{
                    checkDuplicateRestaurantData(restaurantName);
                } catch(DuplicateEntityException badData){
                    log.info(badData.getMessage());
                    isValid = false;
                }
            }
        }while(!isValid);

        restaurantAddress = DataInputUtils.addressInput(scanner);
        Meal[] selecetedMeal = SelectedInputUtils.selectedMeals(scanner, meals);
        Chef[] selectedChef = SelectedInputUtils.selectedChefs(scanner, chefs);
        Waiter[] selectedWaiter = SelectedInputUtils.selectedWaiters(scanner, waiters);
        Deliverer[] selectedDeliverer = SelectedInputUtils.selectedDeliverers(scanner, deliverers);

        long id = restaurantIdCounter++;

        return new Restaurant(id, restaurantName, restaurantAddress, selecetedMeal, selectedChef, selectedWaiter, selectedDeliverer);

    }

    /**
     * Unosi podatke za narudžbu, uključujući odabir restorana, jela, dostavljača i vremena dostave.
     * Ova metoda također provodi provjere unosa i validira format vremena dostave.
     * @param scanner Scanner objekt za unos podataka
     * @param restaurants Array restorana koji su dostupni za narudžbe
     * @param meals Array jela koja su dostupna za narudžbe
     * @param deliverers Array dostavljača koji su dostupni za narudžbe
     * @return Nova narudžba s unesenim podacima
     */

    public static Order orderInput(Scanner scanner, Restaurant[] restaurants, Meal[] meals, Deliverer[] deliverers) {
        Meal[] selectedMeals = new Meal[meals.length];
        Restaurant selectedRestaurant = null;
        LocalDateTime deliveryTime = null;
        Deliverer selectedDeliverer = null;
        boolean isValid = false;
        int mealCount = 0;

        do {
            isValid = true;
            System.out.println("Popis restorana, odaberite jedan brojem 1-" + restaurants.length + ": ");
            for (int i = 0; i < restaurants.length; i++) {
                System.out.println((i + 1) + ". " + restaurants[i].getName());
            }
            try{
                int restaurantChoice = scanner.nextInt();
                scanner.nextLine();

                if (restaurantChoice >= 1 && restaurantChoice <= restaurants.length) {
                    selectedRestaurant = restaurants[restaurantChoice - 1];
                } else {
                    System.out.println("Krivi unos, pokušajte ponovo.");
                    isValid = false;
                }
            }catch(InputMismatchException badData){
                log.info(Messages.INVALID_RESTAURANT_INPUT);
                scanner.nextLine();
                isValid = false;
            }
        } while(!isValid);

        // Odabir jela iz odabranog restorana
        do {
            isValid = true;
            Meal[] availableMeals = selectedRestaurant.getMeals();
            if (availableMeals == null || availableMeals.length == 0) {
                System.out.println("Nema dostupnih jela za restoran " + selectedRestaurant.getName());
                isValid = false;
                continue;
            }

            System.out.println("Popis jela za restoran " + selectedRestaurant.getName() + ", odaberite jedan brojem 1-" + availableMeals.length);
            for (int i = 0; i < availableMeals.length; i++) {
                if (availableMeals[i] != null) {
                    System.out.println((i + 1) + ". " + availableMeals[i].getName());
                }
            }

            try{
                int mealChoice = scanner.nextInt();
                scanner.nextLine();
                if (mealChoice == 0) {
                    break;
                }

                if (mealChoice >= 1 && mealChoice <= availableMeals.length) {
                    if (mealCount < selectedMeals.length) {
                        selectedMeals[mealCount] = availableMeals[mealChoice - 1];
                        mealCount++;
                    } else {
                        log.info("Prekoračili ste maksimalni broj jela (10).");
                        isValid = false;
                    }
                } else {
                    log.info("Krivi unos, pokušajte ponovo.");
                    isValid = false;
                }
            }catch(InputMismatchException badData){
                log.info(Messages.INVALID_MEAL_INPUT);
                scanner.nextLine();
                isValid = false;
            }

        } while(!isValid || mealCount == 0);

        // Odabir dostavljača
        do {
            isValid = true;
            System.out.println("Popis dostavljača, odaberite jednog brojem 1-" + deliverers.length + ": ");
            for (int i = 0; i < deliverers.length; i++) {
                System.out.println((i + 1) + ". " + deliverers[i].getFirstName() + " " + deliverers[i].getLastName());
            }

            try{
                int delivererChoice = scanner.nextInt();
                scanner.nextLine();

                if (delivererChoice >= 1 && delivererChoice <= deliverers.length) {
                    selectedDeliverer = deliverers[delivererChoice - 1];
                    selectedDeliverer.incrementDostave();
                } else {
                    log.info("Krivi unos, pokušajte ponovo.");
                    isValid = false;
                }
            }catch(InputMismatchException badData){
                log.info(Messages.INVALID_DELIVERER_INPUT);
                scanner.nextLine();
                isValid = false;
            }

        } while (!isValid);

        // Trim the selectedMeals array to the number of meals actually selected
        Meal[] finalSelectedMeals = new Meal[mealCount];
        for (int i = 0; i < mealCount; i++) {
            finalSelectedMeals[i] = selectedMeals[i];
        }

        do {
            isValid = true;
            System.out.println("Unesite vrijeme dostave (u formatu: yyyy-MM-dd HH:mm): ");
            String vrijemeDostaveInput = scanner.nextLine();

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                deliveryTime = LocalDateTime.parse(vrijemeDostaveInput, formatter);

                // Provjera da li je vrijeme u budućnosti
                if (deliveryTime.isBefore(LocalDateTime.now())) {
                    log.info("Vrijeme dostave mora biti u budućnosti. Pokušajte ponovo.");
                    isValid = false;
                }

            } catch (DateTimeParseException e) {
                log.info("Neispravan format datuma i vremena. Pokušajte ponovo.");
                scanner.nextLine();
                isValid = false;
            }

        } while (!isValid);

        long id = orderIdCounter++;
        System.out.println("Uspješna narudžba!");

        return new Order(id, selectedRestaurant, finalSelectedMeals, selectedDeliverer, deliveryTime);
    }
}
