package hr.java.utils;
import hr.java.restaurant.exception.DuplicateEntityException;
import hr.java.restaurant.exception.InvalidAmountException;
import hr.java.restaurant.model.Address;
import hr.java.restaurant.model.Category;
import hr.java.restaurant.model.Ingredient;
import hr.java.restaurant.model.Meal;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;
import static hr.java.production.main.Main.log;
import static hr.java.utils.ExceptionUtils.*;

public class DataInputUtils {
    private static long categoryIdCounter = 0;
    private static long ingredientIdCounter = 0;
    private static long mealIdCounter = 0;
    private static long addressIdCounter = 0;

    public static Category categoryInput(Scanner scanner) {
        String categoryName, categoryDescription;
        boolean isValid = false;

        do {
            isValid = true;
            System.out.println("Unesite ime kategorije: ");
            categoryName = scanner.nextLine();
            if (categoryName.isEmpty() || categoryName.length() < 3) {
                log.info(Messages.INVALID_CATEGORY_INPUT);
                isValid = false;
            } else{
                try{
                    checkDuplicateCategoryData(categoryName);
                } catch(DuplicateEntityException badData){
                    log.info(badData.getMessage());
                    isValid = false;
                }
            }
        } while(!isValid);

        do {
            isValid = true;
            System.out.println("Unesite opis kategorije: ");
            categoryDescription = scanner.nextLine();
            if (categoryDescription.isEmpty() || categoryDescription.length() < 3) {
                log.info(Messages.INVALID_CATEGORY_INPUT);
                isValid = false;
            }
        } while(!isValid);

        long id = categoryIdCounter++;
        return new Category(id, categoryName, categoryDescription);
    }

    public static Ingredient ingredientInput(Scanner scanner, Category[] categories) {
        String ingredientName, preparationMethod;
        Category selectedCategory = null;
        Boolean isValid = false;
        BigDecimal kcal = null;
        int categoryChoice = 0;

        do {
            isValid = true;
            System.out.println("Unesite ime sastojka: ");
            ingredientName = scanner.nextLine();
            if (ingredientName.isEmpty() || ingredientName.length() < 3) {
                log.info(Messages.INVALID_INGREDIENT_INPUT);
                isValid = false;
            }else{
                try{
                    checkDuplicateIngredientData(ingredientName);
                }catch(DuplicateEntityException badData){
                    log.info(badData.getMessage());
                    isValid = false;
                }
            }
        } while(!isValid);

        selectedCategory = SelectedInputUtils.selectedCategory(scanner, categories);

        do {
            isValid = true;
            System.out.println("Unesite broj kilokalorija: ");
            try{
                kcal = scanner.nextBigDecimal();
                scanner.nextLine();

            }catch(InputMismatchException badData){
                log.info(Messages.INVALID_INGREDIENT_INPUT);
                scanner.nextLine();
                isValid = false;
            }catch(InvalidAmountException badData){
                log.info(badData.getMessage());
                isValid = false;
            }
        } while(!isValid);

        do {
            isValid = true;
            System.out.println("Unesite metodu preparacije: ");
            preparationMethod = scanner.nextLine();
            if (preparationMethod.isEmpty() || preparationMethod.length() < 3) {
                log.info(Messages.INVALID_INGREDIENT_INPUT);
                isValid = false;
            }
        } while(!isValid);

        long id = ingredientIdCounter++;
        return new Ingredient(id, ingredientName, selectedCategory, kcal, preparationMethod);
    }

    public static Meal mealsInput(Scanner scanner, Category[] categories, Ingredient[] ingredients){
        Category selectedCategory = null;
        Boolean isValid = false;
        BigDecimal price = null;
        Integer ingredientsNumber = 0, calories = 0;
        String mealName;

        do{
            isValid = true;
            System.out.println("Unesite ime jela: ");
            mealName = scanner.nextLine();
            if (mealName.isEmpty() || mealName.length() < 3) {
                log.info(Messages.INVALID_MEAL_INPUT);
                isValid = false;
            } else {
                try{
                    checkDuplicateMealData(mealName);
                } catch(DuplicateEntityException badData){
                    log.info(badData.getMessage());
                    isValid = false;
                }
            }
        }while(!isValid);

        selectedCategory = SelectedInputUtils.selectedCategory(scanner, categories);
        Ingredient[] selectedIngredient = SelectedInputUtils.selectedIngredients(scanner, categories, ingredients);

       do{
           isValid = true;
           System.out.println("Unesite cijenu: ");
           try{
               price = scanner.nextBigDecimal();
               scanner.nextLine();
               validatePrice(price);
               isValid = true;
           }catch (InvalidAmountException badData){
               log.info(badData.getMessage());
               isValid = false;
           } catch(InputMismatchException badData){
               log.info(Messages.INVALID_MEAL_INPUT);
               scanner.nextLine();
               isValid = false;
           }
       } while(!isValid);

        do {
            isValid = true;
            System.out.println("Unesite broj kilokalorija: ");
            try{
                calories = scanner.nextInt();
                scanner.nextLine();

            }catch(InputMismatchException badData){
                System.out.println(Messages.INVALID_MEAL_INPUT);
                scanner.nextLine();
                isValid = false;
            }catch(InvalidAmountException badData){
                log.info(badData.getMessage());
                isValid = false;
            }
        } while(!isValid);

        long id = mealIdCounter++;
        return new Meal(id, mealName, selectedCategory, selectedIngredient, price, calories);
    }

    public static Address addressInput(Scanner scanner){
        String street, houseNumber, city, postalCode;
        Boolean isValid = false;

        do{
            isValid = true;
            System.out.println("Unesite ulicu: ");
            street = scanner.nextLine();
            if (street.isEmpty() || street.length() < 2) {
                log.info(Messages.INVALID_ADDRESS_INPUT);
                isValid = false;
            }
        }while(!isValid);

        do{
            isValid = true;
            System.out.println("Unesite kucnu adresu: ");
            houseNumber = scanner.nextLine();
            if (houseNumber.isEmpty() || houseNumber.length() < 2) {
                log.info(Messages.INVALID_ADDRESS_INPUT);
                isValid = false;
            }
        }while(!isValid);

        do{
            isValid = true;
            System.out.println("Unesite grad: ");
            city = scanner.nextLine();
            if (city.length() < 2) {
                log.info(Messages.INVALID_ADDRESS_INPUT);
                isValid = false;
            }
        }while(!isValid);

        do{
            isValid = true;
            System.out.println("Unesite postanski broj: ");
            postalCode = scanner.nextLine();
            if (postalCode.length() < 1) {
                log.info(Messages.INVALID_ADDRESS_INPUT);
                isValid = false;
            }
        }while(!isValid);

        long id = addressIdCounter++;

        return new Address.BuilderAddress(id)
                .atStreet(street)
                .atHouseNumber(houseNumber)
                .atCity(city)
                .atPostalCode(postalCode)
                .build();
    }
}
