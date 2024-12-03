package hr.java.restaurant.model;

import hr.java.restaurant.enums.ContractType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Predstavlja ugovor o radu zaposlenika u restoranskom sustavu.
 * Klasa sadrži informacije o plaći, vremenskim okvirima ugovora te vrsti ugovora.
 * Dostupne su dvije vrste ugovora: "FULL_TIME" i "PART_TIME".
 */

public class Contract {
    private BigDecimal salary;
    private LocalDate startTime;
    private LocalDate endTime;
    private ContractType contractType;

    /**
     * Konstruktor za stvaranje novog ugovora s plaćom, vremenskim okvirima i vrstom ugovora.
     * @param salary plaća zaposlenika prema ugovoru.
     * @param startTime startTime datum početka ugovora.
     * @param endTime endTime datum završetka ugovora.
     * @param contractType enumeracija contractType, moze biti FULL_TIME, PART_TIME ili NOT_DEFINED
     */

    public Contract(BigDecimal salary, LocalDate startTime, LocalDate endTime, ContractType contractType) {
        this.salary = salary.compareTo(BigDecimal.ZERO) == 0 ? null : salary;
        this.startTime = startTime;
        this.endTime = endTime;
        setContractType(contractType);
    }

    public Optional<BigDecimal> getSalary() {
        return Optional.ofNullable(salary);
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary.compareTo(BigDecimal.ZERO) == 0 ? null : salary;
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

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        if (!contractType.equals(contractType.FULL_TIME) && !contractType.equals(contractType.PART_TIME)){
            System.out.println("Krivi unos ugovora!");
        } else{
            this.contractType = contractType;
        }
    }
}
