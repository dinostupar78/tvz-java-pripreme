package hr.java.production.main;

import hr.java.restaurant.model.*;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    private static final Integer numberOfCategories = 3;
    private static final Integer numberOfIngredients = 2;
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


    }

    public static boolean isNumber(String input) {
        for (int i = 0; i < input.length(); i++) {
            char znak = input.charAt(i);
            if (Character.isDigit(znak)) {
                return true;
            }
        }
        return false;
    }

    public static Category categoryInput(Scanner scanner) {
        String imeKategorije;
        String opisKategorije;

        do {
            System.out.println("Unesite ime kategorije: ");
            imeKategorije = scanner.nextLine();
            if (imeKategorije.length() < 3 || isNumber(imeKategorije)) {
                System.out.println("Krivi unos, unesite ime kategorije koje ne sadrži brojeve i ima barem 3 slova.");
            }
        } while (imeKategorije.length() < 3 || isNumber(imeKategorije));

        do {
            System.out.println("Unesite opis kategorije: ");
            opisKategorije = scanner.nextLine();
            if (opisKategorije.length() < 3 || isNumber(opisKategorije)) {
                System.out.println("Krivi unos, unesite opis kategorije koji ne sadrži brojeve i ima barem 3 slova.");
            }
        } while (opisKategorije.length() < 3 || isNumber(opisKategorije));

        return new Category(imeKategorije, opisKategorije);
    }

    public static Ingredient ingredientInput(Scanner scanner, Category[] categories) {
        String imeSastojka;
        BigDecimal kcal;
        String metodaPreparacije;
        Category odabranaKategorija = null;

        do{
            System.out.println("Unesite ime sastojka: ");
            imeSastojka = scanner.nextLine();
            if (imeSastojka.length() < 3 || isNumber(imeSastojka)) {
                System.out.println("Krivi unos, unesite ime sastojka koji ne sadrži brojeve i ima barem 3 slova.");
            }

        }while(imeSastojka.length() < 3 || isNumber(imeSastojka));

        do{
            System.out.println("Popis kategorija, odaberite jednu brojem 1-3: ");
            for (int i = 0; i < categories.length; i++) {
                System.out.println((i + 1) + ". " + categories[i].getName());
            }

            int categoryChoice = scanner.nextInt();
            scanner.nextLine();

            if (categoryChoice >= 1 && categoryChoice <= categories.length) {
                odabranaKategorija = categories[categoryChoice - 1];
            } else {
                System.out.println("Krivi unos, pokušajte ponovo.");
            }
        }while(imeSastojka.length() < 3 || isNumber(imeSastojka));

        do{
            System.out.println("Unesite broj kcal: ");
            kcal = scanner.nextBigDecimal();
            scanner.nextLine();
            if(kcal.compareTo(BigDecimal.ZERO) < 0 || kcal.compareTo(BigDecimal.valueOf(1000)) > 0){
                System.out.println("Krivi unos, unesite točan broj kcal.");
            }

        }while(kcal.compareTo(BigDecimal.ZERO) < 0 || kcal.compareTo(BigDecimal.valueOf(1000)) > 0);

        do{
            System.out.println("Unesite metodu preparacije: ");
            metodaPreparacije = scanner.nextLine();
            if (metodaPreparacije.length() < 3 || isNumber(metodaPreparacije)) {
                System.out.println("Krivi unos, unesite metodu preparacije koji ne sadrži brojeve i ima barem 3 slova.");
            }

        }while(metodaPreparacije.length() < 3 || isNumber(metodaPreparacije));

        return new Ingredient(imeSastojka, odabranaKategorija, kcal, metodaPreparacije);

    }

    public static Meal mealsInput(Scanner scanner, Category[] categories, Ingredient[] ingredients){
        String imeJela;
        Category odabranaKategorija = null;
        Ingredient odabraniSastojak = null;
        BigDecimal price;

        do{
            System.out.println("Unesite ime jela: ");
            imeJela = scanner.nextLine();
            if (imeJela.length() < 3 || isNumber(imeJela)) {
                System.out.println("Krivi unos, unesite ime jela koji ne sadrži brojeve i ima barem 3 slova.");
            }
        }while(imeJela.length() < 3 || isNumber(imeJela));

        do{
            System.out.println("Popis kategorija, odaberite jednu brojem 1-3: ");
            for (int i = 0; i < categories.length; i++) {
                System.out.println((i + 1) + ". " + categories[i].getName());
            }

            int categoryChoice = scanner.nextInt();
            scanner.nextLine();

            if (categoryChoice >= 1 && categoryChoice <= categories.length) {
                odabranaKategorija = categories[categoryChoice - 1];
            } else {
                System.out.println("Krivi unos, pokušajte ponovo.");
            }
        }while(imeJela.length() < 3 || isNumber(imeJela));

        do{
            System.out.println("Popis sastojaka, odaberite jedan brojem 1-3: ");
            for(int i=0; i < ingredients.length; i++) {
                System.out.println((i + 1) + ". " + ingredients[i].getName());
            }

                int ingredientChoice = scanner.nextInt();
                scanner.nextLine();

                if(ingredientChoice >= 1 && ingredientChoice <= ingredients.length){
                    odabraniSastojak = ingredients[ingredientChoice - 1];
                } else {
                    System.out.println("Krivi unos, pokušajte ponovo.");
                }

        }while(imeJela.length() < 3 || isNumber(imeJela));

        do{
            System.out.println("Unesite cijenu: ");
            price = scanner.nextBigDecimal();
            scanner.nextLine();
            if(price.compareTo(BigDecimal.ZERO) < 0 || price.compareTo(BigDecimal.valueOf(10000)) > 0){
                System.out.println("Krivi unos, unesite točnu cijenu.");
            }

        }while(price.compareTo(BigDecimal.ZERO) < 0 || price.compareTo(BigDecimal.valueOf(1000)) > 0);



        return new Meal(imeJela, odabranaKategorija, odabraniSastojak, price);
    }
    
}
