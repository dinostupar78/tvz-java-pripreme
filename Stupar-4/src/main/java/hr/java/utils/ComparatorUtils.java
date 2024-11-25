package hr.java.utils;

import hr.java.restaurant.model.Person;
import hr.java.restaurant.model.Restaurant;
import hr.java.restaurant.sort.EmployeeContractComparator;
import hr.java.restaurant.sort.EmployeeSalaryComparator;

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
            Person highestEmployeedEmploee = employees.get(0);

            System.out.println("\nRestoran: " + restaurant.getName());
            System.out.println("Zaposlenik s najdužim ugovorom: " + highestEmployeedEmploee.getFirstName()
                    + " " + highestEmployeedEmploee.getLastName() + " - Ugovor: " + highestEmployeedEmploee.getContract().getStartTime());
        }
    }



}
