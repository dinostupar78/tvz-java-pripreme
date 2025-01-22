package hr.javafx.restaurant.sort;

import hr.javafx.restaurant.model.Person;

import java.math.BigDecimal;
import java.util.Comparator;

/**
 * Klasa koja implementira sučelje {@link Comparator} za usporedbu objekata klase {@link Person} prema visini plaće.
 * Usporedba se vrši na temelju plaće zaposlenika, gdje zaposlenici s većom plaćom dolaze prije onih s manjom plaćom (silazno sortiranje).
 */

public class EmployeeSalaryComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        BigDecimal salary1 = p1.getContract().getSalary();
        BigDecimal salary2 = p2.getContract().getSalary();

        return salary2.compareTo(salary1);
    }


}
