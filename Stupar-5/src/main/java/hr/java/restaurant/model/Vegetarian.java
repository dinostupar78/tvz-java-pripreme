package hr.java.restaurant.model;

/**
 * Interafce koji označava obrok kao vegetarijanski.
 * Klase koje implementiraju ovaj interfejs pružaju informacije specifične za vegetarijanske obroke,
 * kao što su preporučena priprema, preporučeni umaci i ocjena cijene.
 * Ovaj Interafce može biti implementiran od strane različitih tipova obroka koji mogu biti vegetarijanski,
 * kao što su tjestenine, vegetarijanske salate, i drugi obroci bez mesa.
 */

public sealed interface Vegetarian permits PastaMeal {

    boolean isAlDente();

    String getSauceRecommendation();

    boolean isLowPrice();
}
