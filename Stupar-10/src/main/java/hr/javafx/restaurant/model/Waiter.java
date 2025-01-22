package hr.javafx.restaurant.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Klasa koja predstavlja konobara u restoranu.
 * Konobar je zaposlenik koji ima ugovor o radu, bonus, te osobne podatke kao što su ime i prezime.
 * Klasa omogućuje pristup podacima konobara i njegovim atributima vezanim uz ugovor i bonus.
 * Također, omogućuje kreiranje objekata konobara pomoću obrasca dizajnera (Builder pattern).
 */

public class Waiter extends Person implements Serializable {
    private Contract contract;
    private Bonus bonusKonobara;

    /**
     * Konstruktor koji inicijalizira podatke konobara.
     * @param id jedinstveni identifikator konobara
     * @param firstName ime konobara
     * @param lastName prezime konobara
     * @param contract ugovor konobara
     * @param bonusKonobara bonus koji konobar prima
     */

    public Waiter(Long id, String firstName, String lastName, Contract contract, Bonus bonusKonobara) {
        super(id, firstName, lastName);
        this.contract = contract;
        this.bonusKonobara = bonusKonobara;
    }

    @Override
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Bonus getBonusKonobara() {
        return bonusKonobara;
    }

    public void setBonusKonobara(Bonus bonusKonobara) {
        this.bonusKonobara = bonusKonobara;
    }

    /**
     * Builder obrazac za izgradnju objekta klase Deliverer.
     */

    public static class BuilderWaiter {
        private Long id;
        private String firstName;
        private String lastName;
        private Contract contract;
        private Bonus bonusKonobara;

        public BuilderWaiter(Long id) {
            this.id = id;
        }

        public BuilderWaiter waiterFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public BuilderWaiter waiterLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public BuilderWaiter waiterContract(Contract contract) {
            this.contract = contract;
            return this;
        }

        public BuilderWaiter waiterBonusKonobara(Bonus bonusKonobara) {
            this.bonusKonobara = bonusKonobara;
            return this;
        }

        public Waiter build() {
            return new Waiter(id, firstName, lastName, contract, bonusKonobara);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Waiter waiter = (Waiter) o;
        return Objects.equals(contract, waiter.contract) && Objects.equals(bonusKonobara, waiter.bonusKonobara);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contract, bonusKonobara);
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + " - Salary: " + contract.getSalary();
    }
}