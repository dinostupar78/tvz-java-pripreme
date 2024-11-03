package hr.java.restaurant.model;

import java.math.BigDecimal;

public class Chef extends Person{

    private BigDecimal salary;

    public Chef(Long id, String firstName, String lastName, BigDecimal salary) {
        super(id, firstName, lastName);
        this.salary = salary;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}

