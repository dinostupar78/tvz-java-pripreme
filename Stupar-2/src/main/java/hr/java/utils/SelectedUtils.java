package hr.java.utils;
import hr.java.restaurant.model.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SelectedUtils {

    public static Category selectedCategory(Scanner scanner, Category[] categories) {
        Category odabranaKategorija = null;
        boolean jeIspravan = false;
        int categoryChoice;
        do {
            jeIspravan = true;
            System.out.println("Popis kategorija, odaberite jednu brojem 1-" + categories.length + ": ");
            for (int i = 0; i < categories.length; i++) {
                System.out.println((i + 1) + ". " + categories[i].getName());
            }
            try {
                categoryChoice = scanner.nextInt();
                scanner.nextLine();
                if (categoryChoice >= 1 && categoryChoice <= categories.length) {
                    odabranaKategorija = categories[categoryChoice - 1];
                } else {
                    System.out.println(Messages.INVALID_CATEGORY_INPUT);
                    jeIspravan = false;
                }
            } catch (InputMismatchException e) {
                System.out.println(Messages.INVALID_CATEGORY_INPUT);
                scanner.nextLine();
                jeIspravan = false;
            }
        } while (!jeIspravan);

        return odabranaKategorija;
    }

    public static Ingredient[] selectedIngredients(Scanner scanner, Category[] categories, Ingredient[] ingredients){
        Boolean jeIspravan = false;
        Ingredient[] odabraniSastojak = new Ingredient[ingredients.length];
        int brojSastojka = 0;
        do{
            jeIspravan = true;
            System.out.println("Popis sastojaka, birate sastojke dok ne unesete 0 ");
            for(int i=0; i < ingredients.length; i++) {
                System.out.println((i + 1) + ". " + ingredients[i].getName());
            }

            int ingredientChoice = scanner.nextInt();
            scanner.nextLine();

            if(ingredientChoice >= 1 && ingredientChoice <= categories.length){
                odabraniSastojak[brojSastojka] = ingredients[ingredientChoice - 1];
                brojSastojka++;

            } else {
                System.out.println(Messages.INVALID_INGREDIENT_INPUT);
                jeIspravan = false;
                continue;
            }
            while(ingredientChoice != 0){
                ingredientChoice = scanner.nextInt();
                scanner.nextLine();
                if(ingredientChoice >= 1 && ingredientChoice <= categories.length){
                    odabraniSastojak[brojSastojka] = ingredients[ingredientChoice - 1];
                    brojSastojka++;

                }else if(ingredientChoice == 0) {
                    jeIspravan = true;
                    break;
                }else{
                    System.out.println(Messages.INVALID_INGREDIENT_INPUT);
                    jeIspravan = false;
                }
            }
        }while(!jeIspravan);

        Ingredient[] sastojak = new Ingredient[brojSastojka];

        for (int i = 0; i < brojSastojka; i++) {
            sastojak[i] = odabraniSastojak[i];
        }
        return sastojak;
    }

    public static Meal[] selectedMeals(Scanner scanner, Meal[] meals){
        Meal[] hranaRestorana = new Meal[meals.length];
        Boolean jeIspravan = false;
        int brojJela = 0;

        do {
            jeIspravan = true;
            System.out.println("Popis jela, birate jela dok ne unesete 0 ");
            for (int i = 0; i < meals.length; i++) {
                if (meals[i] != null) { // Provjeravaj da jelo nije null
                    System.out.println((i + 1) + ". " + meals[i].getName());
                }
            }
            int mealChoice = scanner.nextInt();
            scanner.nextLine();

            while (mealChoice != 0) {
                if (mealChoice >= 1 && mealChoice <= meals.length && meals[mealChoice - 1] != null) {
                    hranaRestorana[brojJela] = meals[mealChoice - 1];
                    brojJela++; // Inkrementiraj broj jela
                } else {
                    System.out.println(Messages.INVALID_RESTAURANT_INPUT);
                    jeIspravan = false;
                }
                mealChoice = scanner.nextInt();
                scanner.nextLine();
            }
        } while (!jeIspravan);

        Meal[] jelo = new Meal[brojJela];
        for(int i=0; i<brojJela; i++){
            jelo[i] = hranaRestorana[i];
        }
        return jelo;
    }

    public static Chef[] selectedChefs(Scanner scanner, Chef[] chefs){
        Boolean jeIspravan = false;
        Chef[] kuhariRestorana = new Chef[chefs.length];
        int brojKuhara = 0;
        do{
            jeIspravan = true;
            System.out.println("Popis kuhara, birate kuhare dok ne unesete 0 ");
            for(int i=0; i < chefs.length; i++) {
                System.out.println((i + 1) + ". " + chefs[i].getFirstName() + " " + chefs[i].getLastName());
            }

            int chefChoice = scanner.nextInt();
            scanner.nextLine();

            if(chefChoice >= 1 && chefChoice <= chefs.length){
                kuhariRestorana[brojKuhara] = chefs[chefChoice - 1];
                brojKuhara++;

            } else {
                System.out.println(Messages.INVALID_CHEF_INPUT);
                jeIspravan = false;
            }
        }while(!jeIspravan);

        Chef[] kuhar = new Chef[brojKuhara];

        for(int i=0; i < brojKuhara; i++){
            kuhar[i] = kuhariRestorana[i];
        }
        return kuhar;
    }

    public static Waiter[] selectedWaiters (Scanner scanner, Waiter[] waiters){
        Boolean jeIspravan = false;
        Waiter[] konobariRestorana = new Waiter[waiters.length];
        int brojKonobara = 0;
        do{
            jeIspravan = true;
            System.out.println("Popis konobara, odaberite jednog brojem 1-3: ");
            for (int i = 0; i < waiters.length; i++) {
                System.out.println((i + 1) + ". " + waiters[i].getFirstName() + " " + waiters[i].getLastName());
            }

            int waiterChoice = scanner.nextInt();
            scanner.nextLine();

            if (waiterChoice >= 1 && waiterChoice <= waiters.length) {
                konobariRestorana[brojKonobara] = waiters[waiterChoice - 1];
                brojKonobara++;
            } else {
                System.out.println(Messages.INVALID_WAITER_INPUT);
                jeIspravan = false;
            }
        }while(!jeIspravan);

        Waiter[] konobar = new Waiter[brojKonobara];

        for(int i=0;i < brojKonobara;i++){
            konobar[i] = konobariRestorana[i];
        }
        return konobar;
    }

    public static Deliverer[] selectedDeliverers(Scanner scanner, Deliverer[] deliverers){
        Boolean jeIspravan = false;
        Deliverer[] dostavljaciRestorana = new Deliverer[deliverers.length];
        int brojDostavljaca = 0;
        do{
            jeIspravan = true;
            System.out.println("Popis dostavljača, odaberite jednog brojem 1-3: ");
            for (int i = 0; i < deliverers.length; i++) {
                System.out.println((i + 1) + ". " + deliverers[i].getFirstName() + " " + deliverers[i].getLastName());
            }

            int delivererChoice = scanner.nextInt();
            scanner.nextLine();

            if (delivererChoice >= 1 && delivererChoice <= deliverers.length) {
                dostavljaciRestorana[brojDostavljaca] = deliverers[delivererChoice - 1];
                brojDostavljaca++;
            } else {
                System.out.println(Messages.INVALID_DELIVERER_INPUT);
                jeIspravan = false;
            }
        }while(!jeIspravan);

        Deliverer[] dostavljac = new Deliverer[brojDostavljaca];
        for(int i = 0; i < brojDostavljaca; i++){
            dostavljac[i] = dostavljaciRestorana[i];
        }

        return dostavljac;
    }

}
