package hr.java.restaurant.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Contract {
    public static final String FULL_TIME = "FULL_TIME";
    public static final String PART_TIME = "PART_TIME";

    private BigDecimal salary;
    private LocalDate startTime;
    private LocalDate endTime;
    private String contractType;

    public Contract(BigDecimal salary, LocalDate startTime, LocalDate endTime, String contractType) {
        this.salary = salary;
        this.startTime = startTime;
        this.endTime = endTime;
        setContractType(contractType);
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        if (!contractType.equals(FULL_TIME) && !contractType.equals(PART_TIME)){
            System.out.println("Krivi unos ugovora!");
        } else{
            this.contractType = contractType;
        }
    }
}
