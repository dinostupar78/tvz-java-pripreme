package hr.javafx.restaurant.exception;

/**
 * Izuzetak koji se baca kada je unesena nevažeća količina ili iznos u sustav.
 * Ova klasa nasljeđuje {@link RuntimeException} i pruža različite konstruktore
 * koji omogućuju pružanje detaljnih informacija o izuzetku, uključujući uzrok
 * i mogućnost praćenja stack traga.
 */

public class InvalidAmountException extends RuntimeException{
    public InvalidAmountException() {
    }

    public InvalidAmountException(String message) {
        super(message);
    }

    public InvalidAmountException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAmountException(Throwable cause) {
        super(cause);
    }

    /**
     * Kreira novi izuzetak s danom porukom, uzrokom, te opcijama za omogućavanje
     * supresije i praćenje stack traga.
     * @param message Poruka koja opisuje razlog izuzetka.
     * @param cause Uzrok koji je prouzročio ovaj izuzetak.
     * @param enableSuppression Da li omogućiti supresiju izuzetaka.
     * @param writableStackTrace Da li stack trag treba biti zapisiv.
     */

    public InvalidAmountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
