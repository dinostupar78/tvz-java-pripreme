package hr.java.utils;
import hr.java.restaurant.model.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import static hr.java.production.main.Main.log;

public class SelectedInputUtils {

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

    public static Ingredient[] selectedIngredients(Scanner scanner, Category[] categories, Ingredient[] ingredients){
        Boolean isValid = false;
        Ingredient[] selectedIngredient = new Ingredient[ingredients.length];
        int ingredientNumber = 0;
        do{
            isValid = true;
            System.out.println("Popis sastojaka, birate sastojke dok ne unesete 0 ");
            for(int i=0; i < ingredients.length; i++) {
                System.out.println((i + 1) + ". " + ingredients[i].getName());
            }
            try{
                int ingredientChoice = scanner.nextInt();
                scanner.nextLine();
                if(ingredientChoice >= 1 && ingredientChoice <= categories.length){
                    selectedIngredient[ingredientNumber] = ingredients[ingredientChoice - 1];
                    ingredientNumber++;

                } else {
                    System.out.println(Messages.INVALID_INGREDIENT_INPUT);
                    isValid = false;
                    continue;
                }
                while(ingredientChoice != 0){
                    ingredientChoice = scanner.nextInt();
                    scanner.nextLine();
                    if(ingredientChoice >= 1 && ingredientChoice <= categories.length){
                        selectedIngredient[ingredientNumber] = ingredients[ingredientChoice - 1];
                        ingredientNumber++;

                    }else if(ingredientChoice == 0) {
                        isValid = true;
                        break;
                    }else{
                        System.out.println(Messages.INVALID_INGREDIENT_INPUT);
                        isValid = false;
                    }
                }
            }catch(InputMismatchException badData){
                log.info(Messages.INVALID_INGREDIENT_INPUT);
                scanner.nextLine();
                isValid = false;
            }
        }while(!isValid);

        Ingredient[] ingredient = new Ingredient[ingredientNumber];

        for (int i = 0; i < ingredientNumber; i++) {
            ingredient[i] = selectedIngredient[i];
        }
        return ingredient;
    }

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
