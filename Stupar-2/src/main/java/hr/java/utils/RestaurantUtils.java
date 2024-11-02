package hr.java.utils;
import hr.java.restaurant.model.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class RestaurantUtils {
    public static Restaurant restoranInput(Scanner scanner, Address[] addresses, Meal[] meals, Chef[] chefs, Waiter[] waiters, Deliverer[] deliverers) {
        Address adresaRestorana = null;
        String imeRestorana;
        int brojDostavljaca = 0, brojKonobara = 0, brojKuhara = 0, brojJela = 0;
        Boolean jeIspravan = false;

        do{
            jeIspravan = true;
            System.out.println("Unesite ime restorana: ");
            imeRestorana = scanner.nextLine();
            if (imeRestorana.isEmpty() || imeRestorana.length() < 3) {
                System.out.println(Messages.INVALID_RESTAURANT_INPUT);
                jeIspravan = false;
            }

        }while(!jeIspravan);

        adresaRestorana = DataUtils.addressInput(scanner);

        Meal[] odabranoJelo = SelectedUtils.selectedMeals(scanner, meals);

        Chef[] odabraniKuhar = SelectedUtils.selectedChefs(scanner, chefs);

        Waiter[] odabraniKonobar = SelectedUtils.selectedWaiters(scanner, waiters);

        Deliverer[] odabraniDostavljac = SelectedUtils.selectedDeliverers(scanner, deliverers);

        return new Restaurant(imeRestorana, adresaRestorana, odabranoJelo, odabraniKuhar, odabraniKonobar, odabraniDostavljac);

    }

    public static Order orderInput(Scanner scanner, Restaurant[] restaurants, Meal[] meals, Deliverer[] deliverers) {
        Meal[] selectedMeals = new Meal[meals.length];
        Restaurant selectedRestaurant = null;
        LocalDateTime vrijemeDostave = null;
        Deliverer selectedDeliverer = null;
        boolean jeIspravan = false;
        int mealCount = 0;

        // Odabir restorana
        do {
            jeIspravan = true;
            System.out.println("Popis restorana, odaberite jedan brojem 1-" + restaurants.length + ": ");
            for (int i = 0; i < restaurants.length; i++) {
                System.out.println((i + 1) + ". " + restaurants[i].getName());
            }

            int restaurantChoice = scanner.nextInt();
            scanner.nextLine();

            if (restaurantChoice >= 1 && restaurantChoice <= restaurants.length) {
                selectedRestaurant = restaurants[restaurantChoice - 1];
            } else {
                System.out.println("Krivi unos, pokušajte ponovo.");
                jeIspravan = false;
            }
        } while (!jeIspravan);

        // Odabir jela iz odabranog restorana
        do {
            jeIspravan = true;

            if (selectedRestaurant == null) {
                System.out.println("Odabrani restoran je nevažeći. Pokušajte ponovo.");
                jeIspravan = false;
                continue;
            }

            Meal[] availableMeals = selectedRestaurant.getMeals();
            if (availableMeals == null || availableMeals.length == 0) {
                System.out.println("Nema dostupnih jela za restoran " + selectedRestaurant.getName());
                jeIspravan = false;
                continue;
            }

            System.out.println("Popis jela za restoran " + selectedRestaurant.getName() + ", odaberite jedan brojem 1-" + availableMeals.length);
            for (int i = 0; i < availableMeals.length; i++) {
                if (availableMeals[i] != null) {
                    System.out.println((i + 1) + ". " + availableMeals[i].getName());
                }
            }

            int mealChoice = scanner.nextInt();
            scanner.nextLine();

            if (mealChoice == 0) {
                // User chose to finish selecting meals
                break; // Exit the loop
            }

            if (mealChoice >= 1 && mealChoice <= availableMeals.length) {
                if (mealCount < selectedMeals.length) {
                    selectedMeals[mealCount] = availableMeals[mealChoice - 1];
                    mealCount++;
                } else {
                    System.out.println("Prekoračili ste maksimalni broj jela (10).");
                    jeIspravan = false;
                }
            } else {
                System.out.println("Krivi unos, pokušajte ponovo.");
                jeIspravan = false;
            }
        } while (!jeIspravan || mealCount == 0);

        // Odabir dostavljača
        do {
            jeIspravan = true;
            System.out.println("Popis dostavljača, odaberite jednog brojem 1-" + deliverers.length + ": ");
            for (int i = 0; i < deliverers.length; i++) {
                System.out.println((i + 1) + ". " + deliverers[i].getFirstName() + " " + deliverers[i].getLastName());
            }

            int delivererChoice = scanner.nextInt();
            scanner.nextLine();

            if (delivererChoice >= 1 && delivererChoice <= deliverers.length) {
                selectedDeliverer = deliverers[delivererChoice - 1];
                selectedDeliverer.incrementDostave();
            } else {
                System.out.println("Krivi unos, pokušajte ponovo.");
                jeIspravan = false;
            }
        } while (!jeIspravan);

        // Trim the selectedMeals array to the number of meals actually selected
        Meal[] finalSelectedMeals = new Meal[mealCount];
        for (int i = 0; i < mealCount; i++) {
            finalSelectedMeals[i] = selectedMeals[i];
        }

        do {
            jeIspravan = true;
            System.out.println("Unesite vrijeme dostave (u formatu: yyyy-MM-dd HH:mm): ");
            String vrijemeDostaveInput = scanner.nextLine();

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                vrijemeDostave = LocalDateTime.parse(vrijemeDostaveInput, formatter);

                // Provjera da li je vrijeme u budućnosti
                if (vrijemeDostave.isBefore(LocalDateTime.now())) {
                    System.out.println("Vrijeme dostave mora biti u budućnosti. Pokušajte ponovo.");
                    jeIspravan = false;
                }

            } catch (DateTimeParseException e) {
                System.out.println("Neispravan format datuma i vremena. Pokušajte ponovo.");
                jeIspravan = false;
            }

        } while (!jeIspravan);

        return new Order(selectedRestaurant, finalSelectedMeals, selectedDeliverer, vrijemeDostave);
    }
}
