package hr.javafx.restaurant.exception;

/**
 * Izuzetak koji se baca kada dođe do pokušaja dupliciranja entiteta u sustavu.
 * Ova klasa nasljeđuje {@link Exception} i pruža različite konstruktore
 * za omogućavanje detaljnijih informacija o izuzetku, kao i mogućnost za
 * prijenos uzroka i specifičnih opcija praćenja stack traga.
 */

public class DuplicateEntityException extends Exception{
    public DuplicateEntityException() {
    }

    public DuplicateEntityException(String message) {
        super(message);
    }

    public DuplicateEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateEntityException(Throwable cause) {
        super(cause);
    }

    /**
     * Kreira novi izuzetak s danom porukom, uzrokom, te opcijama za omogućavanje
     * supresije i praćenje stack traga.
     * @param message message Poruka koja opisuje razlog izuzetka.
     * @param cause cause Uzrok koji je prouzročio ovaj izuzetak.
     * @param enableSuppression enableSuppression Da li omogućiti supresiju izuzetaka.
     * @param writableStackTrace writableStackTrace Da li stack trag treba biti zapisiv.
     */

    public DuplicateEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
