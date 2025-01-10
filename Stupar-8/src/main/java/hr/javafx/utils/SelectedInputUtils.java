package hr.javafx.utils;

import hr.javafx.restaurant.model.*;

import java.util.*;

import static hr.javafx.main.InputMain.log;

/**
 * Pomoćna klasa koja pruža metode za odabir kategorija, sastojaka, jela, kuhara, konobara i dostavljača.
 * Ove metode vode korisnika kroz niz unosa kako bi odabrao odgovarajuće opcije, te provode validaciju unosa.
 * Metode osiguravaju ispravan unos podataka i obrađuju neispravne unose s odgovarajućim porukama o pogrešci.
 */
public class SelectedInputUtils {

    /**
     * Potiče korisnika da odabere kategoriju iz popisa dostupnih kategorija.
     * Provodi validaciju unosa korisnika i vraća odabranu kategoriju.
     * @param scanner Objekt Scanner koji se koristi za unos od strane korisnika.
     * @param categories Niz dostupnih objekata kategorija iz kojih korisnik može odabrati.
     * @return Odabrani objekt kategorije.
     */

    public static Category selectedCategory(Scanner scanner, List<Category> categories) {
        Category selectedCategory = null;
        boolean isValid = false;
        int categoryChoice;
        do {
            isValid = true;
            System.out.println("Popis kategorija, odaberite jednu brojem 1-" + categories.size() + ": ");
            for (int i = 0; i < categories.size(); i++) {
                System.out.println((i + 1) + ". " + categories.get(i).getName());
            }
            try {
                categoryChoice = scanner.nextInt();
                scanner.nextLine();
                if (categoryChoice >= 1 && categoryChoice <= categories.size()) {
                    selectedCategory = categories.get(categoryChoice - 1);
                } else {
                    log.info(Messages.INVALID_CATEGORY_INPUT);
                    isValid = false;
                }
            } catch (InputMismatchException badData) {
                log.info(Messages.INVALID_CATEGORY_INPUT);
                scanner.nextLine();
                isValid = false;
            }
        } while(!isValid);

        return selectedCategory;
    }

    /**
     * Potiče korisnika da odabere sastojke iz popisa dostupnih sastojaka.
     * Korisnik može odabrati više sastojaka ili završiti unos unosom 0.
     * @param scanner Objekt Scanner koji se koristi za unos od strane korisnika.
     * @param categories Niz dostupnih kategorija u restoranu (ne koristi se u ovoj metodi, ali je uključen za kompatibilnost).
     * @param ingredients Niz dostupnih sastojaka iz kojih korisnik može odabrati.
     * @return Niz odabranih sastojaka.
     */

    public static Set<Ingredient> selectedIngredients(Scanner scanner, List<Category> categories, Set<Ingredient> ingredients){
        Set<Ingredient> selectedIngredient = new HashSet<>();
        List<Ingredient> ingredientList = new ArrayList<>(ingredients);
        int ingredientChoice = 0;
        Boolean isValid = true, isFirstIngredientSelected = false;
        while(isValid){
            if(!isFirstIngredientSelected){
                System.out.println("Popis sastojaka, birate sastojke dok ne unesete 0 ");
                for (int i = 0; i < ingredientList.size(); i++) {
                    System.out.println((i + 1) + ". " + ingredientList.get(i).getName());
                }
            }
            isFirstIngredientSelected = true;
            try{
                System.out.print("Vaš odabir: ");
                ingredientChoice = scanner.nextInt();
                scanner.nextLine();

                if (ingredientChoice == 0) {
                    System.out.println("Završili ste unos sastojaka.");
                    break;
                }

                if (ingredientChoice >= 1 && ingredientChoice <= ingredientList.size()) {
                    Ingredient chosenIngredient = ingredientList.get(ingredientChoice - 1);

                    if (selectedIngredient.add(chosenIngredient)) {
                        System.out.println("Dodano: " + chosenIngredient.getName());
                    } else {
                        System.out.println("Sastojak je već odabran.");
                    }
                } else {
                    System.out.println(Messages.INVALID_INGREDIENT_INPUT);
                }
            } catch (InputMismatchException e) {
                System.out.println(Messages.INVALID_INGREDIENT_INPUT);
                scanner.nextLine(); //
            }
        }
        return selectedIngredient;
    }

    /**
     * Potiče korisnika da odabere jela iz popisa dostupnih jela u restoranu.
     * Korisnik može odabrati više jela ili završiti unos unosom 0.
     * @param scanner Objekt Scanner koji se koristi za unos od strane korisnika.
     * @param meals Niz dostupnih jela u restoranu iz kojih korisnik može odabrati.
     * @return Niz odabranih jela.
     */

    public static Set<Meal> selectedMeals(Scanner scanner, Set<Meal> meals) {
        List<Meal> mealList = new ArrayList<>(meals);
        Set<Meal> selectedMeal = new HashSet<>();
        Boolean isValid = true, isFirstMealSelected = false;

        while (isValid) {
            if (!isFirstMealSelected) {
                System.out.println("Popis jela, birate jela dok ne unesete 0 ");
                for (int i = 0; i < mealList.size(); i++) {
                    System.out.println((i + 1) + ". " + mealList.get(i).getName());
                }
            }
            isFirstMealSelected = true;

            try {
                System.out.print("Vaš odabir: ");
                int mealChoice = scanner.nextInt();
                scanner.nextLine();

                if (mealChoice == 0) {
                    System.out.println("Završili ste unos jela.");
                    break;
                }

                if (mealChoice >= 1 && mealChoice <= mealList.size()) {
                    Meal chosenMeal = mealList.get(mealChoice - 1);

                    if (selectedMeal.add(chosenMeal)) {
                        System.out.println("Dodano: " + chosenMeal.getName());
                    } else {
                        System.out.println("Jelo je već odabrano.");
                    }
                } else {
                    System.out.println(Messages.INVALID_MEAL_INPUT);
                    isValid = false;
                }
            } catch (InputMismatchException badData) {
                log.info(Messages.INVALID_MEAL_INPUT);
                scanner.nextLine();
                isValid = false;
            }
        }
        return selectedMeal;
    }

    /**
     * Potiče korisnika da odabere kuhare iz popisa dostupnih kuhara.
     * Korisnik može odabrati više kuhara.
     * @param scanner Objekt Scanner koji se koristi za unos od strane korisnika.
     * @param chefs Niz dostupnih kuhara iz kojih korisnik može odabrati.
     * @return Niz odabranih kuhara.
     */

    public static Set<Chef> selectedChefs(Scanner scanner, Set<Chef> chefs) {
        List<Chef> chefList = new ArrayList<>(chefs);
        Set<Chef> selectedChef = new HashSet<>();
        Boolean isValid = true;

        System.out.println("Popis kuhara, birate jednog kuhara: ");
        for (int i = 0; i < chefList.size(); i++) {
            System.out.println((i + 1) + ". " + chefList.get(i).getFirstName() + " " + chefList.get(i).getLastName());
        }

        try {
            System.out.print("Vaš odabir: ");
            int chefChoice = scanner.nextInt();
            scanner.nextLine();

            if (chefChoice >= 1 && chefChoice <= chefList.size()) {
                Chef chosenChef = chefList.get(chefChoice - 1);
                selectedChef.add(chosenChef);

                System.out.println("Dodan kuhar: " + chosenChef.getFirstName() + " " + chosenChef.getLastName());
            } else {
                System.out.println(Messages.INVALID_CHEF_INPUT);
            }
        } catch (InputMismatchException e) {
            log.info(Messages.INVALID_CHEF_INPUT);
            scanner.nextLine();
        }

        return selectedChef;
    }

    /**
     * Potiče korisnika da odabere konobare iz popisa dostupnih konobara.
     * Korisnik može odabrati više konobara.
     * @param scanner Objekt Scanner koji se koristi za unos od strane korisnika.
     * @param waiters Niz dostupnih konobara iz kojih korisnik može odabrati.
     * @return  Niz odabranih konobara.
     */

    public static Set<Waiter> selectedWaiters(Scanner scanner, Set<Waiter> waiters) {
        List<Waiter> waiterList = new ArrayList<>(waiters);
        Set<Waiter> selectedWaiter = new HashSet<>();

        System.out.println("Popis konobara, birate jednog konobara: ");
        for (int i = 0; i < waiterList.size(); i++) {
            System.out.println((i + 1) + ". " + waiterList.get(i).getFirstName() + " " + waiterList.get(i).getLastName());
        }

        try {
            System.out.print("Vaš odabir: ");
            int waiterChoice = scanner.nextInt();
            scanner.nextLine();

            if (waiterChoice >= 1 && waiterChoice <= waiterList.size()) {
                Waiter chosenWaiter = waiterList.get(waiterChoice - 1);
                selectedWaiter.add(chosenWaiter);

                System.out.println("Dodan konobar: " + chosenWaiter.getFirstName() + " " + chosenWaiter.getLastName());
            } else {
                System.out.println(Messages.INVALID_WAITER_INPUT);
            }
        } catch (InputMismatchException e) {
            log.info(Messages.INVALID_WAITER_INPUT);
            scanner.nextLine();
        }

        return selectedWaiter;
    }


    /**
     * Potiče korisnika da odabere dostavljače iz popisa dostupnih dostavljača.
     * Korisnik može odabrati više dostavljača.
     * @param scanner Objekt Scanner koji se koristi za unos od strane korisnika.
     * @param deliverers Niz dostupnih dostavljača iz kojih korisnik može odabrati.
     * @return Niz odabranih dostavljača.
     */

    public static Set<Deliverer> selectedDeliverers(Scanner scanner, Set<Deliverer> deliverers) {
        List<Deliverer> delivererList = new ArrayList<>(deliverers);
        Set<Deliverer> selectedDeliverer = new HashSet<>();

        System.out.println("Popis dostavljača, birate jednog dostavljača: ");
        for (int i = 0; i < delivererList.size(); i++) {
            System.out.println((i + 1) + ". " + delivererList.get(i).getFirstName() + " " + delivererList.get(i).getLastName());
        }

        try {
            System.out.print("Vaš odabir: ");
            int delivererChoice = scanner.nextInt();
            scanner.nextLine();

            if (delivererChoice >= 1 && delivererChoice <= delivererList.size()) {
                Deliverer chosenDeliverer = delivererList.get(delivererChoice - 1);
                selectedDeliverer.add(chosenDeliverer);

                System.out.println("Dodan dostavljač: " + chosenDeliverer.getFirstName() + " " + chosenDeliverer.getLastName());
            } else {
                System.out.println(Messages.INVALID_DELIVERER_INPUT);
            }
        } catch (InputMismatchException e) {
            log.info(Messages.INVALID_DELIVERER_INPUT);
            scanner.nextLine();
        }

        return selectedDeliverer;
    }

}
