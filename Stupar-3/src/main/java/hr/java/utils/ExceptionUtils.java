package hr.java.utils;

import hr.java.restaurant.exception.DuplicateEntityException;
import hr.java.restaurant.exception.InvalidAmountException;

import java.math.BigDecimal;

import static hr.java.production.main.Main.*;

/**
 * Utility klasa koja pruža statičke metode za provjeru duplikata podataka i validaciju unesenih vrijednosti.
 * Klasa sadrži metode za provjeru duplikata različitih entiteta poput kategorija, sastojaka, jela, kuhara, konobara, dostavljača i restorana.
 * Također, nudi metode za validaciju cijena, plaća i kalorijskih vrijednosti.
 */

public class ExceptionUtils {
    // Fixed-size arrays based on configuration from Main
    public static final String[] existingCategoryData = new String[numberOfCategories];
    public static final String[] existingIngredientData = new String[numberOfIngredients];
    public static final String[] existingMealData = new String[numberOfMeals];
    public static final String[] existingChefData = new String[numberOfChefs];
    public static final String[] existingWaiterData = new String[numberOfWaiters];
    public static final String[] existingDelivererData = new String[numberOfWaiters];
    public static final String[] existingRestaurantData = new String[numberOfRestaurants];

    // Counters for each array to track actual entries
    private static int categoryCount = 0;
    private static int ingredientCount = 0;
    private static int mealCount = 0;
    private static int chefCount = 0;
    private static int waiterCount = 0;
    private static int delivererCount = 0;
    private static int restaurantCount = 0;

    // Constants for validations
    public static final BigDecimal MIN_PRICE = new BigDecimal("10"), MAX_PRICE = new BigDecimal("1000");
    public static final BigDecimal MIN_SALARY = new BigDecimal("500"), MAX_SALARY = new BigDecimal("5000");
    public static final BigDecimal MIN_KCAL = new BigDecimal("5"), MAX_KCAL = new BigDecimal("1500");

    /**
     * Provodi provjeru duplikata podataka za kategoriju. Ako kategorija već postoji, baca {@link DuplicateEntityException}.
     * @param data naziv kategorije koji se provjerava
     * @throws DuplicateEntityException ako kategorija već postoji
     */

    public static void checkDuplicateCategoryData(String data) throws DuplicateEntityException {
        for (int i = 0; i < categoryCount; i++) {
            if (data.equals(existingCategoryData[i])) {
                throw new DuplicateEntityException("Kategorija s nazivom '" + data + "' već postoji.");
            }
        }
        if (categoryCount < existingCategoryData.length) {
            existingCategoryData[categoryCount++] = data;
        }
    }

    /**
     * Provodi provjeru duplikata podataka za sastojak. Ako sastojak već postoji, baca {@link DuplicateEntityException}.
     * @param data naziv sastojka koji se provjerava
     * @throws DuplicateEntityException ako sastojak već postoji
     */

    public static void checkDuplicateIngredientData(String data) throws DuplicateEntityException {
        for (int i = 0; i < ingredientCount; i++) {
            if (data.equals(existingIngredientData[i])) {
                throw new DuplicateEntityException("Sastojak s nazivom '" + data + "' već postoji.");
            }
        }
        if (ingredientCount < existingIngredientData.length) {
            existingIngredientData[ingredientCount++] = data;
        }
    }

    /**
     * Provodi provjeru duplikata podataka za jelo. Ako jelo već postoji, baca {@link DuplicateEntityException}.
     * @param data naziv jela koji se provjerava
     * @throws DuplicateEntityException ako jelo već postoji
     */

    public static void checkDuplicateMealData(String data) throws DuplicateEntityException {
        for (int i = 0; i < mealCount; i++) {
            if (data.equals(existingMealData[i])) {
                throw new DuplicateEntityException("Jelo s nazivom '" + data + "' već postoji.");
            }
        }
        if (mealCount < existingMealData.length) {
            existingMealData[mealCount++] = data;
        }
    }

    /**
     * Provodi provjeru duplikata podataka za kuhara. Ako kuhar već postoji, baca {@link DuplicateEntityException}.
     * @param data ime ili prezime kuhara koji se provjerava
     * @throws DuplicateEntityException ako kuhar već postoji
     */

    public static void checkDuplicateChefData(String data) throws DuplicateEntityException {
        for (int i = 0; i < chefCount; i++) {
            if (data.equals(existingChefData[i])) {
                throw new DuplicateEntityException("Kuhar s nazivom '" + data + "' već postoji.");
            }
        }
        if (chefCount < existingChefData.length) {
            existingChefData[chefCount++] = data;
        }
    }

    /**
     * Provodi provjeru duplikata podataka za konobara. Ako konobar već postoji, baca {@link DuplicateEntityException}.
     * @param data ime ili prezime konobara koji se provjerava
     * @throws DuplicateEntityException ako konobar već postoji
     */

    public static void checkDuplicateWaiterData(String data) throws DuplicateEntityException {
        for (int i = 0; i < waiterCount; i++) {
                if (data.equals(existingWaiterData[i])) {
                throw new DuplicateEntityException("Konobar s nazivom '" + data + "' već postoji.");
            }
        }
        if (waiterCount < existingWaiterData.length) {
            existingWaiterData[waiterCount++] = data;
        }
    }

    /**
     * Provodi provjeru duplikata podataka za dostavljača. Ako dostavljač već postoji, baca {@link DuplicateEntityException}.
     * @param data ime ili prezime dostavljača koji se provjerava
     * @throws DuplicateEntityException ako dostavljač već postoji
     */

    public static void checkDuplicateDelivererData(String data) throws DuplicateEntityException {
        for (int i = 0; i < delivererCount; i++) {
            if (data.equals(existingDelivererData[i])) {
                throw new DuplicateEntityException("Dostavljač s nazivom '" + data + "' već postoji.");
            }
        }
        if (delivererCount < existingDelivererData.length) {
            existingDelivererData[delivererCount++] = data;
        }
    }

    /**
     * Provodi provjeru duplikata podataka za restoran. Ako restoran već postoji, baca {@link DuplicateEntityException}.
     * @param data naziv restorana koji se provjerava
     * @throws DuplicateEntityException ako restoran već postoji
     */

    public static void checkDuplicateRestaurantData(String data) throws DuplicateEntityException {
        for (int i = 0; i < restaurantCount; i++) {
            if (data.equals(existingRestaurantData[i])) {
                throw new DuplicateEntityException("Restoran s nazivom '" + data + "' već postoji.");
            }
        }
        if (restaurantCount < existingRestaurantData.length) {
            existingRestaurantData[restaurantCount++] = data;
        }
    }

    /**
     * Validira cijenu prema unaprijed definiranim granicama (10-1000). Ako cijena nije u tom rasponu, baca {@link InvalidAmountException}.
     * @param data cijena koja se provjerava
     * @throws InvalidAmountException ako cijena nije u validnom rasponu
     */

    public static void validatePrice(BigDecimal data) throws InvalidAmountException {
        if (data.compareTo(MIN_PRICE) < 0 || data.compareTo(MAX_PRICE) > 0) {
            throw new InvalidAmountException("Cijena mora biti između " + MIN_PRICE + " i " + MAX_PRICE + ".");
        }
    }

    /**
     * Validira plaću prema unaprijed definiranim granicama (500-5000). Ako plaća nije u tom rasponu, baca {@link InvalidAmountException}.
     * @param data plaća koja se provjerava
     * @throws InvalidAmountException ako plaća nije u validnom rasponu
     */

    public static void validateSalary(BigDecimal data) throws InvalidAmountException {
        if (data.compareTo(MIN_SALARY) < 0 || data.compareTo(MAX_SALARY) > 0) {
            throw new InvalidAmountException("Plaća mora biti između " + MIN_SALARY + " i " + MAX_SALARY + ".");
        }
    }

    /**
     * Validira Kcal prema unaprijed definiranim granicama (50-1500). Ako Kcal nije u tom rasponu, baca {@link InvalidAmountException}.
     * @param data
     * @throws InvalidAmountException
     */

    public static void validateKcal(BigDecimal data) throws InvalidAmountException {
        if (data.compareTo(MIN_KCAL) < 0 || data.compareTo(MAX_KCAL) > 0) {
            throw new InvalidAmountException("Kcal mora biti između " + MIN_KCAL + " i " + MAX_KCAL + ".");
        }
    }
}
