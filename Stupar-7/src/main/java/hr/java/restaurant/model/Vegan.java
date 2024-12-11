package hr.java.restaurant.model;

/**
 * Interface koji označava obrok kao veganski.
 * Klase koje implementiraju ovaj interfejs pružaju informacije specifične za veganske obroke,
 * kao što su preporučeni dressing, provjera alergena i ocjena cijene.
 * Ovaj interfejs može biti implementiran od strane različitih tipova obroka koji mogu biti veganski,
 * kao što su salate, veganski sendviči, juhe i drugi veganski obroci.
 */

public sealed interface Vegan permits SaladMeal {

    String displayDressing();

    boolean containsAllergens();

    boolean isLowPrice();
}
