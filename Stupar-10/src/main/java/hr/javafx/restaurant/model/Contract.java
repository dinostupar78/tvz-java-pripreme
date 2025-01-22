package hr.javafx.restaurant.model;

import hr.javafx.restaurant.enums.ContractType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Contract extends Entity implements Serializable {
    private BigDecimal salary;
    private LocalDate startTime;
    private LocalDate endTime;
    private ContractType contractType;

    public Contract(Long id, BigDecimal salary, LocalDate startTime, LocalDate endTime, ContractType contractType) {
        super(id);
        this.salary = salary;
        this.startTime = startTime;
        this.endTime = endTime;
        this.contractType = contractType;
    }

    public BigDecimal getSalary() {
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
