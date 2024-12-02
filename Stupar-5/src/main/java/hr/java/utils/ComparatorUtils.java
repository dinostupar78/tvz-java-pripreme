package hr.java.utils;
import hr.java.restaurant.generics.RestaurantLabourExchangeOffice;
import hr.java.restaurant.model.Ingredient;
import hr.java.restaurant.model.Meal;
import hr.java.restaurant.model.Person;
import hr.java.restaurant.model.Restaurant;
import hr.java.restaurant.sort.EmployeeContractComparator;
import hr.java.restaurant.sort.EmployeeSalaryComparator;
import hr.java.restaurant.sort.IngredientAlphabeticalComparator;
import hr.java.restaurant.sort.MealRestaurantCountComparator;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Utility klasa koja sadrži metode za sortiranje i ispisivanje podataka o restoranima, zaposlenicima, jelima i sastojcima.
 * Klasa koristi različite komparatore kako bi sortirala podatke i ispisivala korisnicima tražene informacije.
 */

public class ComparatorUtils {
    /**
     * Ispisuje zaposlenika s najvećom plaćom u svakom restoranu.
     * Zaposlenici se sortiraju prema plaći, a za svaki restoran ispisuje se zaposlenik s najvećom plaćom.
     * @param genericRestaurantList lista restorana u kojima će se tražiti zaposlenici
     */

    public static void printHighestPaidEmployeeInRestaurants(RestaurantLabourExchangeOffice<Restaurant> genericRestaurantList) {
        EmployeeSalaryComparator salaryComparator = new EmployeeSalaryComparator();
        for (Restaurant restaurant : genericRestaurantList.getRestaurants()) {
            List<Person> employees = restaurant.getEmployees();

            employees.sort(salaryComparator);
            Person highestPaidEmployee = employees.get(0);

            System.out.println("\nRestoran: " + restaurant.getName());
            System.out.println("Zaposlenik s najvećom plaćom: " + highestPaidEmployee.getFirstName()
                        + " " + highestPaidEmployee.getLastName() + " - Plaća: " + highestPaidEmployee.getContract().getSalary());
        }
    }

    /**
     * Ispisuje zaposlenike restorana sortirane prema trajanju ugovora, od najkraćeg do najdužeg.
     * Za svakog zaposlenika ispisuje trajanje njegovog ugovora.
     * @param genericRestaurantList lista restorana u kojima će se tražiti zaposlenici
     */

    public static void printHighestEmployeedEmployeeInRestaurants(RestaurantLabourExchangeOffice<Restaurant> genericRestaurantList){
        EmployeeContractComparator contractComparator = new EmployeeContractComparator();
        for (Restaurant restaurant : genericRestaurantList.getRestaurants()){
            List<Person> employees = restaurant.getEmployees();

            employees.sort(contractComparator);
            System.out.println("Sortirani zaposlenici po trajanju ugovora:");
            for (Person employee : employees){
                LocalDate start = employee.getContract().getStartTime();
                LocalDate end = employee.getContract().getEndTime();
                long duration = ChronoUnit.DAYS.between(start, end);
                System.out.println(employee.getFirstName() + " " + employee.getLastName() + " - Ugovor: " + start + " do " + end  + " (Trajanje: " + duration + " dana)");
            }
        }
    }

    /**
     * Ispisuje jela sortirana po broju restorana u kojima se mogu naručiti, od jela s najviše restorana do jela s najmanje
     * @param mealRestaurantMap mapa u kojoj je ključ jelo (Meal) i vrijednost lista restorana (List<Restaurant>) u kojima je to jelo dostupno
     */

    public static void printMealsSortedByRestaurantCount(Map<Meal, RestaurantLabourExchangeOffice<Restaurant>> mealRestaurantMap){
        List<Meal> mealList = new ArrayList<>(mealRestaurantMap.keySet());
        mealList.sort(new MealRestaurantCountComparator(mealRestaurantMap));

        System.out.println("\nJela sortirana po broju restorana:");
        for (Meal meal : mealList) {
            //int restaurantCount = mealRestaurantMap.getOrDefault(meal, List.of()).size();
            int restaurantCount = mealRestaurantMap.containsKey(meal) ? mealRestaurantMap.get(meal).getRestaurants().size() : 0;
            System.out.println(meal.getName() + " - Broj restorana: " + restaurantCount);
        }
    }

    /**
     * Ispisuje namirnice korištene u pripremi jela, sortirane abecedno
     * @param meals skup jela (Meal) čiji se sastojci (Ingredient) trebaju ispisati
     */

    public static void printSortedIngredientsAlphabetically(Set<Meal> meals){
        for (Meal meal : meals){
            Set<Ingredient> ingredientsSet = meal.getIngredient();
            List<Ingredient> ingredients = new ArrayList<>(ingredientsSet);

            ingredients.sort(new IngredientAlphabeticalComparator());
            System.out.println("\nJelo: " + meal.getName());
            System.out.println("Korištene namirnice (abecedno):");
            for (Ingredient ingredient : ingredients) {
                System.out.println("- " + ingredient.getName());
            }
        }
    }
}
