package hr.java.utils;
import hr.java.restaurant.exception.DuplicateEntityException;
import hr.java.restaurant.exception.InvalidAmountException;

import java.math.BigDecimal;

import static hr.java.production.main.Main.*;

public class ExceptionUtils {
    public static final String[] existingCategoryData = new String[numberOfCategories];
    public static final String[] existingIngredientData = new String[numberOfIngredients];
    public static final String[] existingMealData = new String[numberOfMeals];
    public static final String[] existingChefData = new String[numberOfChefs];
    public static final String[] existingWaiterData = new String[numberOfWaiters];
    public static final String[] existingDelivererData = new String[numberOfWaiters];
    public static final String[] existingRestaurantData = new String[numberOfRestaurants];
    public static final BigDecimal MIN_PRICE = new BigDecimal("10"), MAX_PRICE = new BigDecimal("1000");
    public static final BigDecimal MIN_SALARY = new BigDecimal("1000"), MAX_SALARY = new BigDecimal("10000");
    public static final BigDecimal MIN_KCAL = new BigDecimal("5"), MAX_KCAL = new BigDecimal("2000");

    public static void checkDuplicateCategoryData(String data) throws DuplicateEntityException {
        if (existingCategoryData.equals(data)) {
            throw new DuplicateEntityException("Kategorija s nazivom '" + data + "' već postoji.");
        }
    }

    public static void checkDuplicateIngredientData(String data) throws DuplicateEntityException {
        if (existingIngredientData.equals(data)) {
            throw new DuplicateEntityException("Sastojak s nazivom '" + data + "' već postoji.");
        }
    }

    public static void checkDuplicateMealData(String data) throws DuplicateEntityException {
        if (existingMealData.equals(data)) {
            throw new DuplicateEntityException("Jelo s nazivom '" + data + "' već postoji.");
        }
    }

    public static void checkDuplicateChefData(String data) throws DuplicateEntityException {
        if (existingChefData.equals(data)) {
            throw new DuplicateEntityException("Kuhar s nazivom '" + data + "' već postoji.");
        }
    }

    public static void checkDuplicateWaiterData(String data) throws DuplicateEntityException {
        if (existingWaiterData.equals(data)) {
            throw new DuplicateEntityException("Konobar s nazivom '" + data + "' već postoji.");
        }
    }

    public static void checkDuplicateDelivererData(String data) throws DuplicateEntityException {
        if (existingDelivererData.equals(data)) {
            throw new DuplicateEntityException("Dostavljač s nazivom '" + data + "' već postoji.");
        }
    }

    public static void checkDuplicateRestaurantData(String data) throws DuplicateEntityException {
        if (existingRestaurantData.equals(data)) {
            throw new DuplicateEntityException("Restoran s nazivom '" + data + "' već postoji.");
        }
    }

    public static void validatePrice(BigDecimal data) throws InvalidAmountException{
        if(data.compareTo(MIN_PRICE) < 0 || data.compareTo(MAX_PRICE) > 0){
            throw new InvalidAmountException("Cijena mora biti između " + MIN_PRICE + " i " + MAX_PRICE + ".");

        }
    }

    public static void validateSalary(BigDecimal data) throws InvalidAmountException{
        if(data.compareTo(MIN_SALARY) < 0 || data.compareTo(MAX_SALARY) > 0){
            throw new InvalidAmountException("Plaća mora biti između " + MIN_SALARY + " i " + MAX_SALARY + ".");
        }
    }

    public static void validateKcal(BigDecimal data) throws InvalidAmountException{
        if(data.compareTo(MIN_KCAL) < 0 || data.compareTo(MAX_KCAL) > 0){
            throw new InvalidAmountException("Kcal mora biti između " + MIN_KCAL + " i " + MAX_KCAL + ".");
        }
    }
}
