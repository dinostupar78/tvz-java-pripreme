package hr.java.production.main;

import hr.java.restaurant.model.*;
import hr.java.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

import static hr.java.utils.MealRestaurantUtils.displayRestaurantsForSelectedMeal;
import static hr.java.utils.RestaurantInputUtils.restoranInput;

/**
 * Glavna klasa aplikacije koja upravlja unosom podataka u sustav te obradom tih podataka.
 * Program omogućava unos podataka za različite entitete kao što su kategorije, sastojci, jela, kuhari, konobari, dostavljači, restorani i narudžbe.
 * Nakon unosa podataka, aplikacija pronalazi zaposlenika s najvećom plaćom, zaposlenika s najdužim ugovorom te ispisuje jelo s najviše i najmanje kalorija.
 * Aplikacija koristi različite pomoćne klase za unos podataka i izračune, te logira ključne akcije tijekom rada.
 */

public class Main {
    public static final int numberOfCategories = 1, numberOfIngredients = 1, numberOfMeals = 1,
            numberOfChefs = 2, numberOfWaiters = 2, numberOfDeliverers = 2,
            numberOfRestaurants = 1, restaurantAddress = 3, numberOfOrders = 1,
            numberOfSpecialMeals = 1;

    public static Logger log = LoggerFactory.getLogger(Main.class);

    /**
     * Glavna metoda aplikacije koja pokreće cijeli proces unos podataka u sustav.
     * Korisnik unosi podatke za različite entitete, uključujući kategorije, sastojke, jela, kuhare, konobare, dostavljače, restorane i narudžbe.
     * Nakon unosa podataka, aplikacija pronalazi zaposlenika s najvećom plaćom i zaposlenika s najdužim ugovorom, te ispisuje jelo s najviše i najmanje kalorija.
     * @param args
     */

    public static void main(String[] args) {
        List<Category> categories = new ArrayList<>();;
        Set<Ingredient> ingredients = new HashSet<>();
        Set<Meal> meals = new HashSet<>();
        Set<Chef> chefs = new HashSet<>();
        Set<Waiter> waiters = new HashSet<>();
        Set<Deliverer> deliverers = new HashSet<>();
        List<Restaurant> restaurants = new ArrayList<>();
        List<Address> addresses = new ArrayList<>();
        List<Order> orderers = new ArrayList<>();
        List<Person> employees = new ArrayList<>();
        List<Meal> specialMeals = new ArrayList<>();
        Map<Meal, List<Restaurant>> mealRestaurantMap = new HashMap<>();

        Scanner scanner = new Scanner(System.in);
        log.info("The application is started...");

        for(int i = 0; i < numberOfCategories; i++){
            System.out.println("Unesite podatke za " + (i + 1) + " kategoriju");
            Category category = categoryInput(scanner);
            categories.add(category);
        }

        for (int i = 0; i < numberOfIngredients; i++) {
            System.out.println("Unesite podatke za " + (i + 1) + " sastojak");
            Ingredient ingredient = ingredientInput(scanner, categories);
            ingredients.add(ingredient);
        }

        for(int i = 0; i < numberOfMeals; i++){
            System.out.println("Unesite podatke za " + (i+1) + " jelo");
            Meal meal = mealsInput(scanner, categories, ingredients);
            meals.add(meal);
        }

        List<String> mealTypes = Arrays.asList("vegansko", "vegetarijansko", "mesno");

        for(int i = 0; i < numberOfSpecialMeals; i++){
            String mealType = mealTypes.get(i);
            System.out.println("Unesite podatke za " + mealType + " jelo");
            Meal specialMeal = inputSpecialMeal(scanner, mealType, categories, ingredients);
            specialMeals.add(specialMeal);
        }

        for(int i = 0; i < numberOfChefs; i++){
            System.out.println("Unesite podatke za " + (i+1) + " kuhara");
            Chef chef = chefInput(scanner);
            chefs.add(chef);
        }

        for(int i = 0; i < numberOfWaiters; i++){
            System.out.println("Unesite podatke za " + (i+1) + " konobara");
            Waiter waiter = waiterInput(scanner);
            waiters.add(waiter);
        }

        for(int i = 0; i < numberOfDeliverers; i++){
            System.out.println("Unesite podatke za " + (i+1) + " dostavljača");
            Deliverer deliverer = delivererInput(scanner);
            deliverers.add(deliverer);
        }

        for(int i = 0; i < numberOfRestaurants; i++){
            System.out.println("Unesite podatke za " + (i+1) + " restoran");
            Restaurant restaurant = restaurantInput(scanner, addresses, meals, chefs, waiters, deliverers);
            restaurants.add(restaurant);
        }

        for(int i = 0; i < numberOfOrders; i++){
            System.out.println("Unesite podatke za " + (i+1) + " narudžbu");
            Order order = orderInput(scanner, restaurants, meals, deliverers);
            orderers.add(order);
        }

        Map<Meal, List<Restaurant>> mealRestaurant = MealRestaurantUtils.mapMealsToRestaurants(restaurants);
        displayRestaurantsForSelectedMeal(scanner, mealRestaurant);

        employees.addAll(chefs);
        employees.addAll(waiters);
        employees.addAll(deliverers);

        Person highestPaidEmployee = findHighestPaidEmployee(employees);
        System.out.println("\nZaposlenik s najvećom plaćom:");
        printEmployeeInfo(highestPaidEmployee);

        Person longestContractEmployee = findLongestContractEmployee(employees);
        System.out.println("Zaposlenik s najdužim ugovorom:");
        printEmployeeInfo(longestContractEmployee);

        printMealWithMinMaxCalories(specialMeals);

        ComparatorUtils.printHighestPaidEmployeeInRestaurants(restaurants);

        ComparatorUtils.printHighestEmployeedEmployeeInRestaurants(restaurants);

        ComparatorUtils.printMealsSortedByRestaurantCount(mealRestaurant);

        ComparatorUtils.printSortedIngredientsAlphabetically(meals);

    }

    public static Category categoryInput(Scanner scanner) {
        return DataInputUtils.categoryInput(scanner);
    }

    public static Ingredient ingredientInput(Scanner scanner, List<Category> categories) {
        return DataInputUtils.ingredientInput(scanner, categories);
    }

    public static Meal mealsInput(Scanner scanner, List<Category> categories, Set<Ingredient> ingredients) {
        return DataInputUtils.mealsInput(scanner, categories, ingredients);
    }

    public static Meal inputSpecialMeal(Scanner scanner, String mealType, List<Category> categories, Set<Ingredient> ingredients) {
        return mealsInput(scanner, categories, ingredients);
    }

    public static Chef chefInput(Scanner scanner) {
        return EmployeeInputUtils.chefInput(scanner);
    }

    public static Waiter waiterInput(Scanner scanner) {
        return EmployeeInputUtils.waiterInput(scanner);
    }

    public static Deliverer delivererInput(Scanner scanner) {
        return EmployeeInputUtils.delivererInput(scanner);
    }

    public static Address addressInput(Scanner scanner) {
        return DataInputUtils.addressInput(scanner);
    }

    public static Restaurant restaurantInput(Scanner scanner, List<Address> addresses, Set<Meal> meals, Set<Chef> chefs, Set<Waiter> waiters, Set<Deliverer> deliverers) {
        return restoranInput(scanner, addresses, meals, chefs, waiters, deliverers);
    }

    public static Order orderInput(Scanner scanner, List<Restaurant> restaurants, Set<Meal> meals, Set<Deliverer> deliverers) {
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

    public static Person findHighestPaidEmployee(List<Person> employees) {
        if (employees == null || employees.isEmpty()) {
            System.out.println("Nema dostupnih zaposlenika.");
            return null;
        }

        Person highestPaid = null;
        BigDecimal highestSalary = BigDecimal.ZERO;

        for (Person employee : employees) {
            if (employee != null) {
                BigDecimal salary = getSalary(employee);
                if (salary != null && salary.compareTo(highestSalary) > 0) { // Ensure salary is not null
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

    public static Person findLongestContractEmployee(List<Person> employees) {
        if (employees == null || employees.isEmpty()) {
            return null;
        }

        Person longestContractEmployee = null;
        long longestDuration = Long.MIN_VALUE;

        for (Person employee : employees) {
            if (employee != null) {
                Contract contract = employee.getContract();
                if (contract != null && contract.getStartTime() != null && contract.getEndTime() != null) {
                    long duration = contract.getEndTime().toEpochDay() - contract.getStartTime().toEpochDay();

                    if (longestContractEmployee == null || duration > longestDuration ||
                            (duration == longestDuration && contract.getStartTime().isBefore(longestContractEmployee.getContract().getStartTime()))) {
                        longestContractEmployee = employee;
                        longestDuration = duration;
                    }
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

    public static void printMealWithMinMaxCalories(List<Meal> specialMeals) {
        if (specialMeals == null || specialMeals.isEmpty()) {
            System.out.println("Nema dostupnih jela.");
            return;
        }

        Meal maxCalorieMeal = specialMeals.get(0);
        Meal minCalorieMeal = specialMeals.get(0);

        for (Meal meal : specialMeals) {
            if (meal != null) {
                if (meal.getCalories() > maxCalorieMeal.getCalories()) {
                    maxCalorieMeal = meal;
                }
                if (meal.getCalories() < minCalorieMeal.getCalories()) {
                    minCalorieMeal = meal;
                }
            }
        }
        System.out.println("\nJelo sa najviše kalorija: ");
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
