package hr.java.restaurant.model;

/**
 * Predstavlja dostavljača u restoranskom sustavu.
 * Klasa sadrži informacije o ugovoru dostavljača, broju dostava koje je obavio
 * te bonusu koji je dodijeljen dostavljaču.
 */

public class Deliverer extends Person {
    private Contract contract;
    private int brojDostava;
    private Bonus bonusDostavljaca;

    /**
     * Privatni konstruktor za stvaranje instanci klase Deliverer.
     * @param id identifikacijski broj dostavljača.
     * @param firstName ime dostavljača.
     * @param lastName prezime dostavljača.
     * @param contract ugovor dostavljača.
     * @param bonusDostavljaca bonus dostavljača.
     */

    private Deliverer(Long id, String firstName, String lastName, Contract contract, Bonus bonusDostavljaca) {
        super(id, firstName, lastName);
        this.contract = contract;
        this.brojDostava = 0;
        this.bonusDostavljaca = bonusDostavljaca;
    }

    public void incrementDostave() {
        this.brojDostava++;
    }

    public int getBrojDostava() {
        return brojDostava;
    }

    public void setBrojDostava(int brojDostava) {
        this.brojDostava = brojDostava;
    }

    @Override
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract salary) {
        this.contract = salary;
    }

    public Bonus getBonusDostavljaca() {
        return bonusDostavljaca;
    }

    public void setBonusDostavljaca(Bonus bonusDostavljaca) {
        this.bonusDostavljaca = bonusDostavljaca;
    }

    /**
     * Builder obrazac za izgradnju objekta klase Deliverer.
     */

    public static class BuilderDeliverer {
        private Long id;
        private String firstName;
        private String lastName;
        private Contract contract;
        private Bonus bonusDostavljaca;

        public BuilderDeliverer(Long id) {
            this.id = id;
        }

        public BuilderDeliverer delivererFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public BuilderDeliverer delivererLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public BuilderDeliverer delivererContract(Contract contract) {
            this.contract = contract;
            return this;
        }

        public BuilderDeliverer delivererBonusDostavljaca(Bonus bonusDostavljaca) {
            this.bonusDostavljaca = bonusDostavljaca;
            return this;
        }

        public Deliverer build() {
            return new Deliverer(id, firstName, lastName, contract, bonusDostavljaca);
        }
    }
}
