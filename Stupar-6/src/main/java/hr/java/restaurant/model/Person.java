package hr.java.restaurant.model;

import java.io.Serializable;

/**
 * Apstraktna klasa koja predstavlja osobu u restoranu.
 * Klasa sadrži osnovne informacije o osobi poput imena, prezimena i bonusa,
 * te pruža apstraktnu metodu za dohvaćanje ugovora koji se primjenjuje na osobu.
 * Ova klasa služi kao osnovna klasa za specifične vrste osoba, kao što su kuhar i dostavljač.
 */

public abstract class Person extends Entity implements Serializable {
    private String firstName;
    private String lastName;
    private Bonus bonus;

    /**
     * Konstruktor koji inicijalizira novu osobu s osnovnim informacijama.
     * @param id jedinstveni identifikator osobe
     * @param firstName ime osobe
     * @param lastName prezime osobe
     */

    public Person(Long id, String firstName, String lastName) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public abstract Contract getContract();

    public Bonus getBonus() {
        return bonus;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }
}
