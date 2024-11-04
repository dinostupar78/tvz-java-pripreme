package hr.java.restaurant.model;

public class Deliverer extends Person {
    private Contract contract;
    private int brojDostava;
    private Bonus bonusDostavljaca;

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

