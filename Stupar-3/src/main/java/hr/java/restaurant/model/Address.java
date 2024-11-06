package hr.java.restaurant.model;

public class Address extends Entity{
    private String street;
    private String houseNumber;
    private String city;
    private String postalCode;

    private Address(Long id, String street, String houseNumber, String city, String postalCode) {
        super(id);
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public static class BuilderAddress{
        private Long id;
        private String street;
        private String houseNumber;
        private String city;
        private String postalCode;

        public BuilderAddress(Long id){
            this.id = id;
        }

        public BuilderAddress atStreet(String street){
            this.street = street;
            return this;
        }

        public BuilderAddress atHouseNumber(String houseNumber){
            this.houseNumber = houseNumber;
            return this;
        }

        public BuilderAddress atCity(String city){
            this.city = city;
            return this;
        }

        public BuilderAddress atPostalCode(String postalCode){
            this.postalCode = postalCode;
            return this;
        }

        public Address build(){
            return new Address(id, street, houseNumber, city, postalCode);
        }
    }
}
