package hr.javafx.restaurant.generics;

import hr.javafx.restaurant.model.Contract;
import hr.javafx.restaurant.model.Person;

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


