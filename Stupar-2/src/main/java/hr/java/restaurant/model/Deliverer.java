package hr.java.restaurant.model;

import java.math.BigDecimal;

public class Deliverer extends Person {
    private BigDecimal salary;
    private int brojDostava;

    public Deliverer(Long id, String firstName, String lastName, BigDecimal salary) {
        super(id, firstName, lastName);
        this.salary = salary;
        this.brojDostava = 0;
    }

    public void incrementDostave() {
        this.brojDostava++;
    }

    public int getBrojDostava() {
        return brojDostava;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}

