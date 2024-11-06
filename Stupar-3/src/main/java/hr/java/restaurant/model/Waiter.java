package hr.java.restaurant.model;

public class Waiter extends Person{
    private Contract contract;
    private Bonus bonusKonobara;

    private Waiter(Long id, String firstName, String lastName, Contract contract, Bonus bonusKonobara) {
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
}