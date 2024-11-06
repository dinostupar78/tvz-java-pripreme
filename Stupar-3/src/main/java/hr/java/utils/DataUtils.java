package hr.java.utils;
import hr.java.restaurant.model.Address;
import hr.java.restaurant.model.Category;
import hr.java.restaurant.model.Ingredient;
import hr.java.restaurant.model.Meal;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DataUtils {
    private static long categoryIdCounter = 0;
    private static long ingredientIdCounter = 0;
    private static long mealIdCounter = 0;
    private static long addressIdCounter = 0;

    public static Category categoryInput(Scanner scanner) {
        String imeKategorije, opisKategorije;
        boolean jeIspravan = false;

        do {
            jeIspravan = true;
            System.out.println("Unesite ime kategorije: ");
            imeKategorije = scanner.nextLine();
            if (imeKategorije.isEmpty() || imeKategorije.length() < 3) {
                System.out.println(Messages.INVALID_CATEGORY_INPUT);
                jeIspravan = false;
            }
        } while (!jeIspravan);

        do {
            jeIspravan = true;
            System.out.println("Unesite opis kategorije: ");
            opisKategorije = scanner.nextLine();
            if (opisKategorije.isEmpty() || opisKategorije.length() < 3) {
                System.out.println(Messages.INVALID_CATEGORY_INPUT);
                jeIspravan = false;
            }
        } while (!jeIspravan);

        long id = categoryIdCounter++;
        return new Category(id, imeKategorije, opisKategorije);
    }

    public static Ingredient ingredientInput(Scanner scanner, Category[] categories) {
        String imeSastojka, metodaPreparacije;
        Category odabranaKategorija = null;
        Boolean jeIspravan = false;
        BigDecimal kcal = null;
        int categoryChoice = 0;

        do {
            jeIspravan = true;
            System.out.println("Unesite ime sastojka: ");
            imeSastojka = scanner.nextLine();
            if (imeSastojka.isEmpty() || imeSastojka.length() < 3) {
                System.out.println(Messages.INVALID_INGREDIENT_INPUT);
                jeIspravan = false;
            }
        } while (!jeIspravan);

        odabranaKategorija = SelectedUtils.selectedCategory(scanner, categories);

        do {
            jeIspravan = true;
            System.out.println("Unesite broj kcal: ");
            try{
                kcal = scanner.nextBigDecimal();
                scanner.nextLine();

            }catch(InputMismatchException badData){
                System.out.println(Messages.INVALID_INGREDIENT_INPUT);
                scanner.nextLine();
                jeIspravan = false;
                continue;
            }
            if (kcal.compareTo(BigDecimal.ZERO) < 0 || kcal.compareTo(BigDecimal.valueOf(10000)) > 0) {
                System.out.println(Messages.INVALID_INGREDIENT_INPUT);
                jeIspravan = false;
            }
        } while (!jeIspravan);

        do {
            jeIspravan = true;
            System.out.println("Unesite metodu preparacije: ");
            metodaPreparacije = scanner.nextLine();
            if (metodaPreparacije.isEmpty() || metodaPreparacije.length() < 3) {
                System.out.println(Messages.INVALID_INGREDIENT_INPUT);
                jeIspravan = false;
            }
        } while (!jeIspravan);

        long id = ingredientIdCounter++;
        return new Ingredient(id, imeSastojka, odabranaKategorija, kcal, metodaPreparacije);
    }

    public static Meal mealsInput(Scanner scanner, Category[] categories, Ingredient[] ingredients){
        Category odabranaKategorija = null;
        Boolean jeIspravan = false;
        BigDecimal price = null;
        Integer brojSastojka = 0, calories = 0;
        String imeJela;

        do{
            jeIspravan = true;
            System.out.println("Unesite ime jela: ");
            imeJela = scanner.nextLine();
            if (imeJela.isEmpty() || imeJela.length() < 3) {
                System.out.println(Messages.INVALID_MEAL_INPUT);
                jeIspravan = false;
            }
        }while(!jeIspravan);

        odabranaKategorija = SelectedUtils.selectedCategory(scanner, categories);

        Ingredient[] odabraniSastojak = SelectedUtils.selectedIngredients(scanner, categories, ingredients);

        do{
            jeIspravan = true;
            System.out.println("Unesite cijenu: ");
            price = scanner.nextBigDecimal();
            scanner.nextLine();
            if(price.compareTo(BigDecimal.ZERO) < 0 || price.compareTo(BigDecimal.valueOf(10000)) > 0){
                System.out.println("Krivi unos, unesite točnu cijenu.");
                jeIspravan = false;
            }

        }while(!jeIspravan);

        do {
            jeIspravan = true;
            System.out.println("Unesite broj kilokalorija: ");
            calories = scanner.nextInt(); // Use nextInt for calorie input
            scanner.nextLine();
            if (calories < 0) {
                System.out.println("Krivi unos, unesite točan broj kilokalorija.");
                jeIspravan = false;
            }
        } while (!jeIspravan);

        long id = mealIdCounter++;
        return new Meal(id, imeJela, odabranaKategorija, odabraniSastojak, price, calories);
    }

    public static Address addressInput(Scanner scanner){
        String ulica, brojKucneAdrese, grad, postanskiBroj;
        Boolean jeIspravan = false;

        do{
            jeIspravan = true;
            System.out.println("Unesite ulicu: ");
            ulica = scanner.nextLine();
            if (ulica.isEmpty() || ulica.length() < 2) {
                System.out.println(Messages.INVALID_ADDRESS_INPUT);
                jeIspravan = false;
            }
        }while(!jeIspravan);

        do{
            jeIspravan = true;
            System.out.println("Unesite kucnu adresu: ");
            brojKucneAdrese = scanner.nextLine();
            if (brojKucneAdrese.isEmpty() || brojKucneAdrese.length() < 2) {
                System.out.println(Messages.INVALID_ADDRESS_INPUT);
                jeIspravan = false;
            }
        }while(!jeIspravan);

        do{
            jeIspravan = true;
            System.out.println("Unesite grad: ");
            grad = scanner.nextLine();
            if (grad.length() < 2) {
                System.out.println(Messages.INVALID_ADDRESS_INPUT);
                jeIspravan = false;
            }
        }while(!jeIspravan);

        do{
            jeIspravan = true;
            System.out.println("Unesite postanski broj: ");
            postanskiBroj = scanner.nextLine();
            if (postanskiBroj.length() < 1) {
                System.out.println(Messages.INVALID_ADDRESS_INPUT);
                jeIspravan = false;
            }
        }while(!jeIspravan);

        long id = addressIdCounter++;

        return new Address.BuilderAddress(id)
                .atStreet(ulica)
                .atHouseNumber(brojKucneAdrese)
                .atCity(grad)
                .atPostalCode(postanskiBroj)
                .build();
    }
}
