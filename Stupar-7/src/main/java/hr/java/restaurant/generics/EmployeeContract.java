package hr.java.restaurant.generics;
import hr.java.restaurant.model.Contract;
import hr.java.restaurant.model.Person;
import java.util.Map;

public class EmployeeContract <T extends Person, S extends Contract>{
    Map<S, T> employeeContracts;

    public EmployeeContract(Map<S, T> employeeContracts) {
        this.employeeContracts = employeeContracts;
    }

    public Map<S, T> getEmployeeContracts() {
        return employeeContracts;
    }

    public void setEmployeeContracts(Map<S, T> employeeContracts) {
        this.employeeContracts = employeeContracts;
    }
}


