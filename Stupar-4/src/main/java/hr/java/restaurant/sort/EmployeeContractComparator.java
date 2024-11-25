package hr.java.restaurant.sort;

import hr.java.restaurant.model.Person;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

public class EmployeeContractComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        LocalDate start1 = p1.getContract().getStartTime();
        LocalDate end1 = p1.getContract().getEndTime();
        long duration1 = ChronoUnit.DAYS.between(start1, end1);

        LocalDate start2 = p2.getContract().getStartTime();
        LocalDate end2 = p2.getContract().getEndTime();
        long duration2 = ChronoUnit.DAYS.between(start2, end2);

        return Long.compare(duration2, duration1); // silazno, za uzlazno (duration1, duration2)

    }
}
