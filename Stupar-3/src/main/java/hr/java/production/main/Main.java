package hr.java.production.main;
import hr.java.restaurant.model.*;
import hr.java.utils.DataInputUtils;
import hr.java.utils.EmployeeInputUtils;
import hr.java.utils.Messages;
import hr.java.utils.RestaurantInputUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.util.Scanner;
import static hr.java.utils.RestaurantInputUtils.restoranInput;

/**
 * Glavna klasa aplikacije koja upravlja unosom podataka u sustav te obradom tih podataka.
 * Program omogućava unos podataka za različite entitete kao što su kategorije, sastojci, jela, kuhari, konobari, dostavljači, restorani i narudžbe.
 * Nakon unosa podataka, aplikacija pronalazi zaposlenika s najvećom plaćom, zaposlenika s najdužim ugovorom te ispisuje jelo s najviše i najmanje kalorija.
 * Aplikacija koristi različite pomoćne klase za unos podataka i izračune, te logira ključne akcije tijekom rada.
 */

public class Main {
    public static final Integer numberOfCategories = 3; // 3
    public static final Integer numberOfIngredients = 3; // 3
    public static final Integer numberOfMeals = 3; // 3
    public static final Integer numberOfChefs = 3; // 3
    public static final Integer numberOfWaiters = 3; // 3
    public static final Integer numberOfDeliverers = 3; // 3
    public static final Integer numberOfRestaurants = 3; // 3
    public static final Integer restaurantAddress = 3; // 3
    public static final Integer numberOfOrders = 3; // 3
    public static final Integer numberOfSpecialMeals = 3;

    public static Logger log = LoggerFactory.getLogger(Main.class);

    /**
     * Glavna metoda aplikacije koja pokreće cijeli proces unos podataka u sustav.
     * Korisnik unosi podatke za različite entitete, uključujući kategorije, sastojke, jela, kuhare, konobare, dostavljače, restorane i narudžbe.
     * Nakon unosa podataka, aplikacija pronalazi zaposlenika s najvećom plaćom i zaposlenika s najdužim ugovorom, te ispisuje jelo s najviše i najmanje kalorija.
     * @param args
     */

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
        Person[] employees = new Person[numberOfChefs + numberOfWaiters + numberOfDeliverers];
        Meal[] specialMeals = new Meal[numberOfSpecialMeals];

        Scanner scanner = new Scanner(System.in);
        log.info("The application is started...");

        for(int i = 0; i < categories.length; i++){
            System.out.println("Unesite podatke za " + (i + 1) + " kategoriju");
            Category category = categoryInput(scanner);
            categories[i] = category;
        }

        for(int i = 0; i < ingredients.length; i++){
            System.out.println("Unesite podatke za " + (i + 1) + " sastojak");
            Ingredient ingredient = ingredientInput(scanner, categories);
            ingredients[i] = ingredient;
        }

        for(int i = 0; i < meals.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " jelo");
            Meal meal = mealsInput(scanner, categories, ingredients);
            meals[i] = meal;
        }

        String[] mealTypes = {"vegansko", "vegetarijansko", "mesno"};

        for(int i = 0; i < specialMeals.length; i++){
            System.out.println("Unesite podatke za " + mealTypes[i] + " jelo");
            Meal meal = inputSpecialMeal(scanner, mealTypes[i], categories, ingredients);
            specialMeals[i] = meal;
        }

        for(int i = 0; i < chefs.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " kuhara");
            Chef chef = chefInput(scanner);
            chefs[i] = chef;
        }

        for(int i = 0; i < waiters.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " konobara");
            Waiter waiter = waiterInput(scanner);
            waiters[i] = waiter;
        }

        for(int i = 0; i < deliverers.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " dostavljača");
            Deliverer deliverer = delivererInput(scanner);
            deliverers[i] = deliverer;
        }

        for(int i = 0; i < restaurants.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " restoran");
            Restaurant restaurant = restoranInput(scanner, addresses, meals, chefs, waiters, deliverers);
            restaurants[i] = restaurant;
        }

        for(int i = 0; i < orderers.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " narudžbu");
            Order order = orderInput(scanner, restaurants, meals, deliverers);
            orderers[i] = order;
        }

        int index = 0;
        for (int i = 0; i < chefs.length; i++) {
            employees[index++] = chefs[i];
        }

        for (int i = 0; i < waiters.length; i++) {
            employees[index++] = waiters[i];
        }

        for (int i = 0; i < deliverers.length; i++) {
            employees[index++] = deliverers[i];
        }

        Person highestPaidEmployee = findHighestPaidEmployee(employees);
        System.out.println("\nZaposlenik s najvećom plaćom:");
        printEmployeeInfo(highestPaidEmployee);

        Person longestContractEmployee = findLongestContractEmployee(employees);
        System.out.println("\nZaposlenik s najdužim ugovorom:");
        printEmployeeInfo(longestContractEmployee);

        printMealWithMinMaxCalories(specialMeals);

    }

    public static Category categoryInput(Scanner scanner) {
        return DataInputUtils.categoryInput(scanner);
    }

    public static Ingredient ingredientInput(Scanner scanner, Category[] categories) {
        return DataInputUtils.ingredientInput(scanner, categories);
    }

    public static Meal mealsInput(Scanner scanner, Category[] categories, Ingredient[] ingredients){
        return DataInputUtils.mealsInput(scanner, categories, ingredients);
    }

    public static Meal inputSpecialMeal(Scanner scanner, String mealType, Category[] categories, Ingredient[] ingredients) {
        return mealsInput(scanner, categories, ingredients);
    }

    public static Chef chefInput(Scanner scanner){
       return EmployeeInputUtils.chefInput(scanner);
    }

    public static Waiter waiterInput(Scanner scanner){
        return EmployeeInputUtils.waiterInput(scanner);
    }

    public static Deliverer delivererInput(Scanner scanner){
        return EmployeeInputUtils.delivererInput(scanner);
    }

    public static Address addressInput(Scanner scanner){
       return DataInputUtils.addressInput(scanner);
    }

    public static Restaurant restaurantInput(Scanner scanner, Address[] addresses, Meal[] meals, Chef[] chefs, Waiter[] waiters, Deliverer[] deliverers){
        return restoranInput(scanner, addresses, meals, chefs, waiters, deliverers);
    }

    public static Order orderInput(Scanner scanner, Restaurant[] restaurants, Meal[] meals, Deliverer[] deliverers) {
        return RestaurantInputUtils.orderInput(scanner, restaurants, meals, deliverers);
    }

    private static BigDecimal getSalary(Person employee) {
        return employee.getContract().getSalary();
    }

    /**
     * Pronalaženje zaposlenika s najvećom plaćom.
     * Ova metoda iterira kroz sve zaposlenike, uspoređuje njihove plaće i vraća zaposlenika s najvišom plaćom.
     * Ako je više zaposlenika s jednakim najvišim plaćama, prvi zaposlenik u redu bit će odabran.
     * @param employees Polje zaposlenika za kojeg se traži onaj s najvećom plaćom.
     * @return Zaposlenik s najvećom plaćom.
     */

    public static Person findHighestPaidEmployee(Person[] employees) {
        Person highestPaid = employees[0];
        BigDecimal highestSalary = getSalary(highestPaid);

        for (Person employee : employees) {
            if (employee != null) {
                BigDecimal salary = getSalary(employee);
                if (salary.compareTo(highestSalary) > 0) {
                    highestPaid = employee;
                    highestSalary = salary;
                }
            }
        }
        return highestPaid;
    }

    /**
     * Pronalaženje zaposlenika s najdužim ugovorom.
     * Ova metoda iterira kroz sve zaposlenike, izračunava trajanje ugovora za svakog zaposlenika,
     * i vraća zaposlenika s najdužim trajanjem ugovora. Ako su trajanja ugovora jednaka,
     * preferira se zaposlenik čiji je ugovor počeo ranije.
     * @param employees Polje zaposlenika za kojeg se traži onaj s najdužim ugovorom.
     * @return Zaposlenik s najdužim ugovorom.
     */

    public static Person findLongestContractEmployee(Person[] employees) {
        Person longestContractEmployee = null;
        long longestDuration = Long.MIN_VALUE;

        for (Person employee : employees) {
            if (employee != null) {
                long duration = employee.getContract().getEndTime().toEpochDay() - employee.getContract().getStartTime().toEpochDay();

                if (longestContractEmployee == null || duration > longestDuration || (duration == longestDuration && employee.getContract().getStartTime().isBefore(longestContractEmployee.getContract().getStartTime()))){
                    longestContractEmployee = employee;
                    longestDuration = duration;
                }
            }
        }
        return longestContractEmployee;
    }

    /**
     * Ispisivanje informacija o zaposleniku, uključujući ime, prezime, plaću i datume početka i završetka ugovora.
     * Ispis koristi predložak iz klase {@link Messages}.
     * @param employee Zaposlenik za kojeg se ispisuju podaci.
     */

    private static void printEmployeeInfo(Person employee) {
        String firstName = employee.getFirstName(), lastName = employee.getLastName();
        Contract contract = employee.getContract();

        System.out.println(String.format(Messages.EMPLOYEE_INFO_MESSAGE, firstName, lastName, contract.getSalary(), contract.getStartTime(), contract.getEndTime()));
    }

    /**
     * Ispisivanje jela s najviše i najmanje kalorija iz danog niza jela.
     * Metoda pronalazi jelo s najviše kalorija i jelo s najmanje kalorija te ispisuje njihove podatke.
     * @param specialMeals Polje jela za koja se traže ona s najviše i najmanje kalorija.
     */

    public static void printMealWithMinMaxCalories(Meal[] specialMeals) {
        Meal maxCalorieMeal = specialMeals[0];
        Meal minCalorieMeal = specialMeals[0];

        for (Meal meal : specialMeals) {
            if (meal.getCalories() > maxCalorieMeal.getCalories()) {
                maxCalorieMeal = meal;
            }
            if (meal.getCalories() < minCalorieMeal.getCalories()) {
                minCalorieMeal = meal;
            }
        }
        System.out.println("Jelo sa najviše kalorija: ");
        printMealInfo(maxCalorieMeal);
        System.out.println("Jelo sa najmanje kalorija: ");
        printMealInfo(minCalorieMeal);
    }

    /**
     * Ispisivanje informacija o jelu, uključujući ime, kategoriju, cijenu i kalorije.
     * Ispis koristi predložak iz klase {@link Messages}.
     * @param specialMeals Jelo za koje se ispisuju podaci.
     */

    private static void printMealInfo(Meal specialMeals) {
        System.out.println(String.format(Messages.MEAL_INFO_MESSAGE,
                specialMeals.getName(),
                specialMeals.getCategory().getName(),
                specialMeals.getPrice(),
                specialMeals.getCalories()));
    }
}
