package hr.java.restaurant.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Predstavlja kuhara u restoranskom sustavu.
 * Kuhar je zaposlenik restorana s ugovorom o radu i mogućim bonusom.
 * Klasa nasljeđuje klasu {@link Person} i dodaje specifične atribute kao što su ugovor i bonus kuhara.
 */

public class Chef extends Person implements Serializable {

    private Contract contract;
    private Bonus BonusKuhara;

    /**
     * Konstruktor za stvaranje novog kuhara s danim atributima.
     * @param id jedinstveni identifikator kuhara.
     * @param firstName ime kuhara.
     * @param lastName prezime kuhara.
     * @param contract ugovor o radu kuhara.
     * @param BonusKuhara bonus kuhara.
     */

    public Chef(Long id, String firstName, String lastName, Contract contract, Bonus BonusKuhara) {
        super(id, firstName, lastName);
        this.contract = contract;
        this.BonusKuhara = BonusKuhara;
    }

    @Override
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Bonus getBonusKuhara() {
        return BonusKuhara;
    }

    public void setBonusKuhara(Bonus BonusKuhara) {
        this.BonusKuhara = BonusKuhara;
    }

    /**
     * Builder obrazac za izgradnju objekta klase Chef.
     */

    public static class BuilderChef{
        private Long id;
        private String firstName;
        private String lastName;
        private Contract contract;
        private Bonus BonusKuhara;

        public BuilderChef(Long id){
            this.id = id;
        }

        public BuilderChef chefFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public BuilderChef chefLastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public BuilderChef chefContract(Contract contract){
            this.contract = contract;
            return this;
        }

        public BuilderChef chefBonusKuhara(Bonus BonusKuhara){
            this.BonusKuhara = BonusKuhara;
            return this;
        }

        public Chef build(){
            return new Chef(id, firstName, lastName, contract, BonusKuhara);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chef chef = (Chef) o;
        return Objects.equals(contract, chef.contract) && Objects.equals(BonusKuhara, chef.BonusKuhara);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contract, BonusKuhara);
    }
}

