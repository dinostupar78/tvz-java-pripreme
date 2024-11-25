package hr.java.restaurant.sort;
import hr.java.restaurant.model.Person;
import java.math.BigDecimal;
import java.util.Comparator;

public class EmployeeSalaryComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        BigDecimal salary1 = p1.getContract().getSalary();
        BigDecimal salary2 = p2.getContract().getSalary();

        return salary2.compareTo(salary1);
    }
}
