package hr.java.restaurant.model;

public class Chef extends Person{

    private Contract contract;
    private Bonus BonusKuhara;

    private Chef(Long id, String firstName, String lastName, Contract contract, Bonus BonusKuhara) {
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
}

