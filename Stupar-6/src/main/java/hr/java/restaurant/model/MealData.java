package hr.java.restaurant.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class MealData implements Serializable {
    private static final long serialVersionUID = 1L;
    private LocalDate date;
    private String firstName;
    private String lastName;
    private String mealName;
    private BigDecimal price;

    public MealData(LocalDate date, String firstName, String lastName, String mealName, BigDecimal price) {
        this.date = date;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mealName = mealName;
        this.price = price;
    }

    // Getteri i toString metoda za ispis
    public LocalDate getDate()
    {
        return date;
    }

    public String getFirstName() {
        return firstName;

    }
    public String getLastName() { return lastName;
    }

    public String getMealName() {
        return mealName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Datum: " + date + "\n" +
                "Ime: " + firstName + "\n" +
                "Prezime: " + lastName + "\n" +
                "Ime jela: " + mealName + "\n" +
                "Cijena: " + price;
    }
}
