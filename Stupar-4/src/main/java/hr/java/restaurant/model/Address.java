package hr.java.restaurant.model;

/**
 * Predstavlja adresu u sustavu.
 * Klasa se koristi za pohranu podataka o ulici, broju kuće, gradu i poštanskoj broju.
 * Adresa se koristi za različite entitete u aplikaciji kao što su restoran i zaposlenici.
 * Ova klasa također koristi dizajner (Builder pattern) za jednostavno stvaranje objekta Adresa.
 */

public class Address extends Entity{
    private String street;
    private String houseNumber;
    private String city;
    private String postalCode;

    /**
     * Privatni konstruktor koji se koristi unutar Buildera za stvaranje instanci klase Address.
     * @param id Jedinstveni identifikator adrese.
     * @param street Ulica adrese.
     * @param houseNumber Broj kuće adrese.
     * @param city Grad adrese.
     * @param postalCode Poštanski broj adrese.
     */

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

    @Override
    public String toString() {
        return street + ", " + city + ", " + postalCode;
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
