package hr.java.restaurant.model;

import hr.java.restaurant.enums.ContractType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class Contract {
    private Optional<BigDecimal> salary; // Using Optional to handle null salaries
    private LocalDate startTime;
    private LocalDate endTime;
    private ContractType contractType;

    public Contract(BigDecimal salary, LocalDate startTime, LocalDate endTime, ContractType contractType) {
        this.salary = Optional.ofNullable(salary);
        this.startTime = startTime;
        this.endTime = endTime;
        this.contractType = contractType;
    }

    public Optional<BigDecimal> getSalary() {
        return salary;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public ContractType getContractType() {
        return contractType;
    }
}
