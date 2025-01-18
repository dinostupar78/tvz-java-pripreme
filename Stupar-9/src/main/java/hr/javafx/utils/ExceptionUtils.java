package hr.javafx.utils;

import hr.javafx.restaurant.enums.ContractType;
import hr.javafx.restaurant.exception.DuplicateEntityException;
import hr.javafx.restaurant.exception.InvalidAmountException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility klasa koja pruža statičke metode za provjeru duplikata podataka i validaciju unesenih vrijednosti.
 * Klasa sadrži metode za provjeru duplikata različitih entiteta poput kategorija, sastojaka, jela, kuhara, konobara, dostavljača i restorana.
 * Također, nudi metode za validaciju cijena, plaća i kalorijskih vrijednosti.
 */
public class ExceptionUtils {

    public static final List<String> existingCategoryData = new ArrayList<>();
    public static final List<String> existingIngredientData = new ArrayList<>();
    public static final List<String> existingMealData = new ArrayList<>();
    public static final List<String> existingChefData = new ArrayList<>();
    public static final List<String> existingWaiterData = new ArrayList<>();
    public static final List<String> existingDelivererData = new ArrayList<>();
    public static final List<String> existingRestaurantData = new ArrayList<>();

    public static final BigDecimal MIN_PRICE = new BigDecimal("10"), MAX_PRICE = new BigDecimal("1000");
    public static final BigDecimal MIN_SALARY = new BigDecimal("500"), MAX_SALARY = new BigDecimal("5000");
    public static final BigDecimal MIN_KCAL = new BigDecimal("5"), MAX_KCAL = new BigDecimal("1500");
    private static final String FULL_TIME = "FULL_TIME";
    private static final String PART_TIME = "PART_TIME";

    /**
     * Provodi provjeru duplikata podataka za kategoriju. Ako kategorija već postoji, baca {@link DuplicateEntityException}.
     * @param data naziv kategorije koji se provjerava
     * @throws DuplicateEntityException ako kategorija već postoji
     */
    public static void checkDuplicateCategoryData(String data) throws DuplicateEntityException {
        if (existingCategoryData.contains(data)) {
            throw new DuplicateEntityException("Kategorija s nazivom '" + data + "' već postoji.");
        }
        existingCategoryData.add(data);
    }

    /**
     * Provodi provjeru duplikata podataka za sastojak. Ako sastojak već postoji, baca {@link DuplicateEntityException}.
     * @param data naziv sastojka koji se provjerava
     * @throws DuplicateEntityException ako sastojak već postoji
     */
    public static void checkDuplicateIngredientData(String data) throws DuplicateEntityException {
        if (existingIngredientData.contains(data)) {
            throw new DuplicateEntityException("Sastojak s nazivom '" + data + "' već postoji.");
        }
        existingIngredientData.add(data);
    }

    /**
     * Provodi provjeru duplikata podataka za jelo. Ako jelo već postoji, baca {@link DuplicateEntityException}.
     * @param data naziv jela koji se provjerava
     * @throws DuplicateEntityException ako jelo već postoji
     */
    public static void checkDuplicateMealData(String data) throws DuplicateEntityException {
        if (existingMealData.contains(data)) {
            throw new DuplicateEntityException("Jelo s nazivom '" + data + "' već postoji.");
        }
        existingMealData.add(data);
    }

    /**
     * Provodi provjeru duplikata podataka za kuhara. Ako kuhar već postoji, baca {@link DuplicateEntityException}.
     * @param data ime ili prezime kuhara koji se provjerava
     * @throws DuplicateEntityException ako kuhar već postoji
     */
    public static void checkDuplicateChefData(String data) throws DuplicateEntityException {
        if (existingChefData.contains(data)) {
            throw new DuplicateEntityException("Kuhar s nazivom '" + data + "' već postoji.");
        }
        existingChefData.add(data);
    }

    /**
     * Provodi provjeru duplikata podataka za konobara. Ako konobar već postoji, baca {@link DuplicateEntityException}.
     * @param data ime ili prezime konobara koji se provjerava
     * @throws DuplicateEntityException ako konobar već postoji
     */
    public static void checkDuplicateWaiterData(String data) throws DuplicateEntityException {
        if (existingWaiterData.contains(data)) {
            throw new DuplicateEntityException("Konobar s nazivom '" + data + "' već postoji.");
        }
        existingWaiterData.add(data);
    }

    /**
     * Provodi provjeru duplikata podataka za dostavljača. Ako dostavljač već postoji, baca {@link DuplicateEntityException}.
     * @param data ime ili prezime dostavljača koji se provjerava
     * @throws DuplicateEntityException ako dostavljač već postoji
     */
    public static void checkDuplicateDelivererData(String data) throws DuplicateEntityException {
        if (existingDelivererData.contains(data)) {
            throw new DuplicateEntityException("Dostavljač s nazivom '" + data + "' već postoji.");
        }
        existingDelivererData.add(data);
    }

    /**
     * Provodi provjeru duplikata podataka za restoran. Ako restoran već postoji, baca {@link DuplicateEntityException}.
     * @param data naziv restorana koji se provjerava
     * @throws DuplicateEntityException ako restoran već postoji
     */
    public static void checkDuplicateRestaurantData(String data) throws DuplicateEntityException {
        if (existingRestaurantData.contains(data)) {
            throw new DuplicateEntityException("Restoran s nazivom '" + data + "' već postoji.");
        }
        existingRestaurantData.add(data);
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

    public static void validateContractType(String contractType) throws InvalidAmountException {
        try {
            ContractType.valueOf(contractType);
        } catch (IllegalArgumentException e) {
            throw new InvalidAmountException("Krivi unos ugovora! Dozvoljeni tipovi su FULL_TIME i PART_TIME.");
        }
    }
}
