package hr.java.restaurant.model;

/**
 * Apstraktna klasa koja predstavlja osnovnu entitetu s jedinstvenim identifikatorom (ID).
 * Ova klasa služi kao osnovna klasa za druge entitete u sustavu, omogućujući im da dijele
 * zajednički atribut ID i metode za njegovu manipulaciju.
 */

public abstract class Entity {
    private Long id;

    public Entity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
