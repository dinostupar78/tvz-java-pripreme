package hr.java.restaurant.model;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Predstavlja ugovor o radu zaposlenika u restoranskom sustavu.
 * Klasa sadrži informacije o plaći, vremenskim okvirima ugovora te vrsti ugovora.
 * Dostupne su dvije vrste ugovora: "FULL_TIME" i "PART_TIME".
 */

public class Contract {
    public static final String FULL_TIME = "FULL_TIME";
    public static final String PART_TIME = "PART_TIME";
    private BigDecimal salary;
    private LocalDate startTime;
    private LocalDate endTime;
    private String contractType;

    /**
     * Konstruktor za stvaranje novog ugovora s plaćom, vremenskim okvirima i vrstom ugovora.
     * @param salary plaća zaposlenika prema ugovoru.
     * @param startTime datum početka ugovora.
     * @param endTime datum završetka ugovora.
     * @param contractType vrsta ugovora (može biti {@link #FULL_TIME} ili {@link #PART_TIME}).
     */

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
