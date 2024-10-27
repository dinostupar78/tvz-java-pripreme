package hr.java.restaurant.model;

import java.math.BigDecimal;

public class Deliverer {
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private int brojDostava;

    public Deliverer(String firstName, String lastName, BigDecimal salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.brojDostava = 0;
    }

    public void incrementDostave() {
        this.brojDostava++;
    }

    public int getBrojDostava() {
        return brojDostava;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}

