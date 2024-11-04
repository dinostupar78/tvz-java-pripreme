package hr.java.production.main;
import hr.java.restaurant.model.*;
import hr.java.utils.DataUtils;
import hr.java.utils.EmployeeUtils;
import hr.java.utils.Messages;
import hr.java.utils.RestaurantUtils;
import java.math.BigDecimal;
import java.util.Scanner;
import static hr.java.utils.RestaurantUtils.restoranInput;

public class Main {
    private static final Integer numberOfCategories = 3; // 3
    private static final Integer numberOfIngredients = 3; // 3
    private static final Integer numberOfMeals = 3; // 3
    private static final Integer numberOfChefs = 3; // 3
    private static final Integer numberOfWaiters = 3; // 3
    private static final Integer numberOfDeliverers = 3; // 3
    private static final Integer numberOfRestaurants = 3; // 3
    private static final Integer restaurantAddress = 3; // 3
    private static final Integer numberOfOrders = 3; // 3

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

        for(int i = 0; i < chefs.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " kuhara");
            Chef kuhara = chefInput(scanner);
            chefs[i] = kuhara;
        }

        for(int i = 0; i < waiters.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " konobara");
            Waiter konobar = waiterInput(scanner);
            waiters[i] = konobar;
        }

        for(int i = 0; i < deliverers.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " dostavljača");
            Deliverer dostavljac = delivererInput(scanner);
            deliverers[i] = dostavljac;
        }

        for(int i = 0; i < restaurants.length; i++){
            System.out.println("Unesite podatke za " + (i+1) + " restoran");
            Restaurant restoran = restoranInput(scanner, addresses, meals, chefs, waiters, deliverers);
            restaurants[i] = restoran;
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

        Meal[] SelectedMeals = createMeals();
        printMealWithMinMaxCalories(meals);

    }

    public static Category categoryInput(Scanner scanner) {
        return DataUtils.categoryInput(scanner);
    }

    public static Ingredient ingredientInput(Scanner scanner, Category[] categories) {
        return DataUtils.ingredientInput(scanner, categories);
    }

    public static Meal mealsInput(Scanner scanner, Category[] categories, Ingredient[] ingredients){
        return DataUtils.mealsInput(scanner, categories, ingredients);
    }

    public static Chef chefInput(Scanner scanner){
       return EmployeeUtils.chefInput(scanner);
    }

    public static Waiter waiterInput(Scanner scanner){
        return EmployeeUtils.waiterInput(scanner);
    }

    public static Deliverer delivererInput(Scanner scanner){
        return EmployeeUtils.delivererInput(scanner);
    }

    public static Address addressInput(Scanner scanner){
       return DataUtils.addressInput(scanner);
    }

    public static Restaurant restaurantInput(Scanner scanner, Address[] addresses, Meal[] meals, Chef[] chefs, Waiter[] waiters, Deliverer[] deliverers){
        return restoranInput(scanner, addresses, meals, chefs, waiters, deliverers);
    }

    public static Order orderInput(Scanner scanner, Restaurant[] restaurants, Meal[] meals, Deliverer[] deliverers) {
        return RestaurantUtils.orderInput(scanner, restaurants, meals, deliverers);
    }

    private static BigDecimal getSalary(Person employee) {
        return employee.getContract().getSalary();
    }

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

    private static void printEmployeeInfo(Person employee) {
        String firstName = employee.getFirstName();
        String lastName = employee.getLastName();
        Contract contract = employee.getContract();

        System.out.println(String.format(Messages.EMPLOYEE_INFO_MESSAGE, firstName, lastName, contract.getSalary(), contract.getStartTime(), contract.getEndTime()));
    }

    public static Meal[] createMeals() {
        Meal[] meals = new Meal[9];

        Category veganCategory = new Category(1L, "Vegan", "Dishes that do not contain any animal products.");
        Category vegetarianCategory = new Category(2L, "Vegetarian", "Dishes that do not contain meat but may contain dairy or eggs.");
        Category meatCategory = new Category(3L, "Meat", "Dishes that contain meat.");

        // Create Vegan Meals
        meals[0] = new SaladMeal(1L, "Vegan Salad", veganCategory, new Ingredient[]{}, BigDecimal.valueOf(50), true, 200);
        meals[1] = new SaladMeal(2L, "Quinoa Salad", veganCategory, new Ingredient[]{}, BigDecimal.valueOf(60), true, 250);
        meals[2] = new SaladMeal(3L, "Fruit Salad", veganCategory, new Ingredient[]{}, BigDecimal.valueOf(30), true, 150);

        // Create Vegetarian Meals
        meals[3] = new PastaMeal(4L, "Vegetarian Pasta", vegetarianCategory, new Ingredient[]{}, BigDecimal.valueOf(70), true, 400);
        meals[4] = new PastaMeal(5L, "Vegetable Lasagna", vegetarianCategory, new Ingredient[]{}, BigDecimal.valueOf(80), true, 450);
        meals[5] = new PastaMeal(6L, "Stuffed Peppers", vegetarianCategory, new Ingredient[]{}, BigDecimal.valueOf(90), true, 350);

        // Create Meat Meals
        meals[6] = new BeefMeal(7L, "Beef Steak", meatCategory, new Ingredient[]{}, BigDecimal.valueOf(120), true, 700);
        meals[7] = new BeefMeal(8L, "Grilled Chicken", meatCategory, new Ingredient[]{}, BigDecimal.valueOf(100), true, 600);
        meals[8] = new BeefMeal(9L, "Pork Chops", meatCategory, new Ingredient[]{}, BigDecimal.valueOf(110), true, 650);

        return meals;
    }

    public static void printMealWithMinMaxCalories(Meal[] meals) {
        Meal maxCalorieMeal = meals[0];
        Meal minCalorieMeal = meals[0];

        for (Meal meal : meals) {
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

    private static void printMealInfo(Meal meal) {
        System.out.println(String.format(Messages.MEAL_INFO_MESSAGE,
                meal.getName(),
                meal.getCategory().getName(),
                meal.getPrice(),
                meal.getCalories()));

    }
}
