package hr.javafx.restaurant.model;

/**
 * Sučelje koje predstavlja ponašanje jela koja sadrže meso.
 * Sučelje definira osnovne metode koje specifična jela moraju implementirati,
 * kao što su preporučeni prilog, način pripreme i provjera cijene.
 * Ovo sučelje je označeno kao sealed, što znači da samo klase koje su specifično dopuštene
 */

public sealed interface Meat permits BeefMeal {

    String getRecommendedSideDish();

    boolean isWellDone();

    boolean isLowPrice();
}
