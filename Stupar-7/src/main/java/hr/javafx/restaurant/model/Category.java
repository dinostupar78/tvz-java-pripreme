package hr.javafx.restaurant.model;

import java.io.Serializable;

/**
 * Predstavlja kategoriju u restoranskom sustavu.
 * Kategorija se koristi za grupiranje jela prema njihovim karakteristikama (npr. "predjelo", "glavno jelo", "desert").
 * Svaka kategorija ima ime i opis koji dodatno pojašnjava njezinu svrhu.
 */

public class Category extends Entity implements Serializable {
    private String name;
    private String description;

    /**
     * Konstruktor za stvaranje nove kategorije s danim ID-em, imenom i opisom.
     * @param id jedinstveni identifikator kategorije.
     * @param name ime kategorije.
     * @param description detaljan opis kategorije.
     */

    public Category( Long id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return " Category: " +
                "name = '" + name + '\'' +
                ", description='" + description;

    }
}
