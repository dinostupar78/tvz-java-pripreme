package hr.java.utils;

import hr.java.restaurant.model.*;

import java.util.*;

import static hr.java.production.main.Main.log;

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

    public static Category selectedCategory(Scanner scanner, Category[] categories) {
        Category selectedCategory = null;
        boolean isValid = false;
        int categoryChoice;
        do {
            isValid = true;
            System.out.println("Popis kategorija, odaberite jednu brojem 1-" + categories.length + ": ");
            for (int i = 0; i < categories.length; i++) {
                System.out.println((i + 1) + ". " + categories[i].getName());
            }
            try {
                categoryChoice = scanner.nextInt();
                scanner.nextLine();
                if (categoryChoice >= 1 && categoryChoice <= categories.length) {
                    selectedCategory = categories[categoryChoice - 1];
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

    public static Set<Ingredient> selectedIngredients(Scanner scanner, Category[] categories, Set<Ingredient> ingredients){
        Set<Ingredient> selectedIngredients = new HashSet<>();
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

                    if (selectedIngredients.add(chosenIngredient)) {
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
        return selectedIngredients;
    }

    /**
     * Potiče korisnika da odabere jela iz popisa dostupnih jela u restoranu.
     * Korisnik može odabrati više jela ili završiti unos unosom 0.
     * @param scanner Objekt Scanner koji se koristi za unos od strane korisnika.
     * @param meals Niz dostupnih jela u restoranu iz kojih korisnik može odabrati.
     * @return Niz odabranih jela.
     */

    public static Meal[] selectedMeals(Scanner scanner, Meal[] meals){
        Meal[] restaurantMeal = new Meal[meals.length];
        Boolean isValid = false;
        int mealNumber = 0;

        do {
            isValid = true;
            System.out.println("Popis jela, birate jela dok ne unesete 0 ");
            for (int i = 0; i < meals.length; i++) {
                if (meals[i] != null) { // Provjeravaj da jelo nije null
                    System.out.println((i + 1) + ". " + meals[i].getName());
                }
            }
            try {
                int mealChoice = scanner.nextInt();
                scanner.nextLine();
                while (mealChoice != 0) {
                    if (mealChoice >= 1 && mealChoice <= meals.length && meals[mealChoice - 1] != null) {
                        restaurantMeal[mealNumber] = meals[mealChoice - 1];
                        mealNumber++; // Inkrementiraj broj jela
                    } else {
                        log.info(Messages.INVALID_RESTAURANT_INPUT);
                        isValid = false;
                    }
                    mealChoice = scanner.nextInt();
                    scanner.nextLine();
                }
            }catch(InputMismatchException badData){
                log.info(Messages.INVALID_MEAL_INPUT);
                scanner.nextLine();
                isValid = false;
            }
        } while (!isValid);

        Meal[] jelo = new Meal[mealNumber];
        for(int i=0; i<mealNumber; i++){
            jelo[i] = restaurantMeal[i];
        }
        return jelo;
    }

    /**
     * Potiče korisnika da odabere kuhare iz popisa dostupnih kuhara.
     * Korisnik može odabrati više kuhara.
     * @param scanner Objekt Scanner koji se koristi za unos od strane korisnika.
     * @param chefs Niz dostupnih kuhara iz kojih korisnik može odabrati.
     * @return Niz odabranih kuhara.
     */

    public static Chef[] selectedChefs(Scanner scanner, Chef[] chefs){
        Boolean isValid = false;
        Chef[] restaurantChefs = new Chef[chefs.length];
        int chefNumber = 0;
        do{
            isValid = true;
            System.out.println("Popis kuhara, birate kuhare dok ne unesete 0 ");
            for(int i=0; i < chefs.length; i++) {
                System.out.println((i + 1) + ". " + chefs[i].getFirstName() + " " + chefs[i].getLastName());
            }
            try{
                int chefChoice = scanner.nextInt();
                scanner.nextLine();
                if(chefChoice >= 1 && chefChoice <= chefs.length){
                    restaurantChefs[chefNumber] = chefs[chefChoice - 1];
                    chefNumber++;
                } else {
                    log.info(Messages.INVALID_CHEF_INPUT);
                    isValid = false;
                }
            }catch(InputMismatchException badData){
                log.info(Messages.INVALID_CHEF_INPUT);
                scanner.nextLine();
                isValid = false;
            }
        }while(!isValid);

        Chef[] chef = new Chef[chefNumber];

        for(int i=0; i < chefNumber; i++){
            chef[i] = restaurantChefs[i];
        }
        return chef;
    }

    /**
     * Potiče korisnika da odabere konobare iz popisa dostupnih konobara.
     * Korisnik može odabrati više konobara.
     * @param scanner Objekt Scanner koji se koristi za unos od strane korisnika.
     * @param waiters Niz dostupnih konobara iz kojih korisnik može odabrati.
     * @return  Niz odabranih konobara.
     */

    public static Waiter[] selectedWaiters (Scanner scanner, Waiter[] waiters){
        Boolean isValid = false;
        Waiter[] restaurantWaiters = new Waiter[waiters.length];
        int waiterNumber = 0;
        do{
            isValid = true;
            System.out.println("Popis konobara, odaberite jednog brojem 1-3: ");
            for (int i = 0; i < waiters.length; i++) {
                System.out.println((i + 1) + ". " + waiters[i].getFirstName() + " " + waiters[i].getLastName());
            }
            try{
                int waiterChoice = scanner.nextInt();
                scanner.nextLine();
                if (waiterChoice >= 1 && waiterChoice <= waiters.length) {
                    restaurantWaiters[waiterNumber] = waiters[waiterChoice - 1];
                    waiterNumber++;
                } else {
                    System.out.println(Messages.INVALID_WAITER_INPUT);
                    isValid = false;
                }
            }catch(InputMismatchException badData){
                log.info(Messages.INVALID_WAITER_INPUT);
                scanner.nextLine();
                isValid = false;
            }
        }while(!isValid);

        Waiter[] waiter = new Waiter[waiterNumber];

        for(int i=0;i < waiterNumber;i++){
            waiter[i] = restaurantWaiters[i];
        }
        return waiter;
    }

    /**
     * Potiče korisnika da odabere dostavljače iz popisa dostupnih dostavljača.
     * Korisnik može odabrati više dostavljača.
     * @param scanner Objekt Scanner koji se koristi za unos od strane korisnika.
     * @param deliverers Niz dostupnih dostavljača iz kojih korisnik može odabrati.
     * @return Niz odabranih dostavljača.
     */

    public static Deliverer[] selectedDeliverers(Scanner scanner, Deliverer[] deliverers){
        Boolean isValid = false;
        Deliverer[] restaurantDeliverers = new Deliverer[deliverers.length];
        int delivererNumber = 0;
        do{
            isValid = true;
            System.out.println("Popis dostavljača, odaberite jednog brojem 1-3: ");
            for (int i = 0; i < deliverers.length; i++) {
                System.out.println((i + 1) + ". " + deliverers[i].getFirstName() + " " + deliverers[i].getLastName());
            }
            try{
                int delivererChoice = scanner.nextInt();
                scanner.nextLine();
                if (delivererChoice >= 1 && delivererChoice <= deliverers.length) {
                    restaurantDeliverers[delivererNumber] = deliverers[delivererChoice - 1];
                    delivererNumber++;
                } else {
                    System.out.println(Messages.INVALID_DELIVERER_INPUT);
                    isValid = false;
                }
            }catch(InputMismatchException badData){
                log.info(Messages.INVALID_DELIVERER_INPUT);
                scanner.nextLine();
                isValid = false;
            }
        }while(!isValid);

        Deliverer[] deliverer = new Deliverer[delivererNumber];
        for(int i = 0; i < delivererNumber; i++){
            deliverer[i] = restaurantDeliverers[i];
        }

        return deliverer;
    }
}
