package hr.java.restaurant.model;
import java.math.BigDecimal;

/**
 * Predstavlja bonus koji se dodaje na osnovnu plaću zaposlenika.
 * Bonus je specifičan iznos koji se dodaje na osnovnu plaću, a njegov iznos se navodi u obliku {@link BigDecimal}.
 * Ovaj tip može biti koristan za razne vrste nagrada ili bonusa koji zaposlenici mogu primiti, a koji nisu dio njihove osnovne plaće.
 * @param iznosBonusaNaOsnovnuPlacu Iznos bonusa koji se dodaje na osnovnu plaću zaposlenika.
 */

public record Bonus(BigDecimal iznosBonusaNaOsnovnuPlacu) {

}

