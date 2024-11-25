package hr.java.utils;
import hr.java.restaurant.model.Person;
import hr.java.restaurant.model.Restaurant;
import hr.java.restaurant.sort.EmployeeContractComparator;
import hr.java.restaurant.sort.EmployeeSalaryComparator;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ComparatorUtils {
    public static void printHighestPaidEmployeeInRestaurants(List<Restaurant> restaurants) {
        EmployeeSalaryComparator salaryComparator = new EmployeeSalaryComparator();
        for (Restaurant restaurant : restaurants) {
            List<Person> employees = restaurant.getEmployees();

            employees.sort(salaryComparator);
            Person highestPaidEmployee = employees.get(0);

            System.out.println("\nRestoran: " + restaurant.getName());
            System.out.println("Zaposlenik s najvećom plaćom: " + highestPaidEmployee.getFirstName()
                        + " " + highestPaidEmployee.getLastName() + " - Plaća: " + highestPaidEmployee.getContract().getSalary());


        }
    }

    public static void printHighestEmployeedEmployeeInRestaurants(List<Restaurant> restaurants){
        EmployeeContractComparator contractComparator = new EmployeeContractComparator();
        for (Restaurant restaurant : restaurants){
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



}
